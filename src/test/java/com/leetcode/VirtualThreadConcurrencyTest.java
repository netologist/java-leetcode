package com.leetcode;

import com.leetcode.p01_lrucache.LRUCache;
import com.leetcode.p12_ratelimiter.TokenBucketRateLimiter;
import com.leetcode.p19_hitcounter.HitCounter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

class VirtualThreadConcurrencyTest {

    @Test
    @DisplayName("1,000 Virtual Threads concurrently accessing LRUCache without data races")
    void testLRUCacheConcurrencyWithVirtualThreads() throws InterruptedException {
        LRUCache<Integer, Integer> cache = new LRUCache<>(50);
        int threadCount = 1_000;

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(threadCount);

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < threadCount; i++) {
                final int key = i % 100;
                final int val = i;
                executor.submit(() -> {
                    try {
                        startLatch.await();
                        cache.put(key, val);
                        cache.get(key);
                    } catch (InterruptedException ignored) {
                    } finally {
                        doneLatch.countDown();
                    }
                });
            }

            startLatch.countDown();
            boolean completed = doneLatch.await(10, TimeUnit.SECONDS);

            assertThat(completed).isTrue();
            assertThat(cache.size()).isLessThanOrEqualTo(50);
        }
    }

    @Test
    @DisplayName("1,000 Virtual Threads concurrently recording hits in HitCounter")
    void testHitCounterConcurrencyWithVirtualThreads() throws InterruptedException {
        HitCounter hitCounter = new HitCounter();
        int threadCount = 1_000;

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(threadCount);

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < threadCount; i++) {
                final int ts = 100 + (i % 50);
                executor.submit(() -> {
                    try {
                        startLatch.await();
                        hitCounter.hit(ts);
                    } catch (InterruptedException ignored) {
                    } finally {
                        doneLatch.countDown();
                    }
                });
            }

            startLatch.countDown();
            boolean completed = doneLatch.await(10, TimeUnit.SECONDS);

            assertThat(completed).isTrue();
            assertThat(hitCounter.getHits(200)).isEqualTo(1_000);
        }
    }

    @Test
    @DisplayName("1,000 Virtual Threads concurrently acquiring from TokenBucketRateLimiter")
    void testTokenBucketConcurrencyWithVirtualThreads() throws InterruptedException {
        TokenBucketRateLimiter limiter = new TokenBucketRateLimiter(500, 100.0);
        int threadCount = 1_000;

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(threadCount);
        AtomicInteger permitsAcquired = new AtomicInteger(0);

        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < threadCount; i++) {
                executor.submit(() -> {
                    try {
                        startLatch.await();
                        if (limiter.tryAcquire()) {
                            permitsAcquired.incrementAndGet();
                        }
                    } catch (InterruptedException ignored) {
                    } finally {
                        doneLatch.countDown();
                    }
                });
            }

            startLatch.countDown();
            boolean completed = doneLatch.await(10, TimeUnit.SECONDS);

            assertThat(completed).isTrue();
            assertThat(permitsAcquired.get()).isGreaterThan(0);
            assertThat(permitsAcquired.get()).isLessThanOrEqualTo(600); // Bound by initial 500 + burst refill
        }
    }
}
