package com.leetcode.p12_ratelimiter;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Custom FinTech Systems: Token Bucket Rate Limiter.
 * <p>
 * <b>FinTech Application:</b> Stripe/Revolut Public API Gateway throttling, merchant burst capacity control,
 * payment card network TPS enforcement (Stripe, Revolut, Adyen).
 * <p>
 * <b>Algorithm:</b> Lazy Token Refill using high-resolution monotonic timestamps.<br>
 * <b>Time Complexity:</b> O(1) lock-free atomic acquire.<br>
 * <b>Space Complexity:</b> O(1).
 */
public class TokenBucketRateLimiter {

    private final long capacity;
    private final double refillRateTokensPerSecond;
    private final AtomicLong availableTokens;
    private final AtomicLong lastRefillTimestampNanos;

    public TokenBucketRateLimiter(long capacity, double refillRateTokensPerSecond) {
        if (capacity <= 0 || refillRateTokensPerSecond <= 0) {
            throw new IllegalArgumentException("Capacity and refill rate must be strictly positive");
        }
        this.capacity = capacity;
        this.refillRateTokensPerSecond = refillRateTokensPerSecond;
        this.availableTokens = new AtomicLong(capacity);
        this.lastRefillTimestampNanos = new AtomicLong(System.nanoTime());
    }

    /**
     * Tries to acquire 1 token.
     */
    public boolean tryAcquire() {
        return tryAcquire(1);
    }

    /**
     * Tries to acquire requested number of tokens atomically.
     */
    public boolean tryAcquire(int tokensRequested) {
        if (tokensRequested <= 0) return true;

        refill();

        while (true) {
            long currentTokens = availableTokens.get();
            if (currentTokens < tokensRequested) {
                return false;
            }
            if (availableTokens.compareAndSet(currentTokens, currentTokens - tokensRequested)) {
                return true;
            }
        }
    }

    private void refill() {
        long now = System.nanoTime();
        long last = lastRefillTimestampNanos.get();
        long elapsedNanos = now - last;

        if (elapsedNanos <= 0) return;

        double tokensToAdd = (elapsedNanos / 1_000_000_000.0) * refillRateTokensPerSecond;
        if (tokensToAdd >= 1.0 && lastRefillTimestampNanos.compareAndSet(last, now)) {
            while (true) {
                long current = availableTokens.get();
                long updated = Math.min(capacity, current + (long) tokensToAdd);
                if (availableTokens.compareAndSet(current, updated)) {
                    break;
                }
            }
        }
    }

    public long getAvailableTokens() {
        refill();
        return availableTokens.get();
    }

    public long getCapacity() {
        return capacity;
    }
}
