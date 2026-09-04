package com.leetcode.p19_hitcounter;

import java.util.concurrent.locks.ReentrantLock;

/**
 * LeetCode #362: Design Hit Counter.
 * <p>
 * <b>FinTech Application:</b> Real-time Transactions Per Second (TPS) metering, error rate circuit breakers,
 * API burst tracking over rolling 5-minute (300 seconds) windows (Stripe, Datadog).
 * <p>
 * <b>Data Structure:</b> Circular Bucket Buffer (Array of 300 timestamps + 300 hit counts).<br>
 * <b>Time Complexity:</b> O(1) for hit() and getHits().<br>
 * <b>Space Complexity:</b> O(1) strictly constant 300 slots.
 */
public class HitCounter {

    private static final int WINDOW_SECONDS = 300;

    private final ReentrantLock lock = new ReentrantLock();
    private final int[] times = new int[WINDOW_SECONDS];
    private final int[] hits = new int[WINDOW_SECONDS];

    /**
     * Record a hit at given timestamp (in seconds).
     */
    public void hit(int timestamp) {
        lock.lock();
        try {
            int idx = timestamp % WINDOW_SECONDS;
            if (times[idx] != timestamp) {
                // New second bucket: reset and overwrite stale window data
                times[idx] = timestamp;
                hits[idx] = 1;
            } else {
                // Same second bucket: increment hit count
                hits[idx]++;
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns the number of hits in the past 5 minutes (300 seconds) from given timestamp.
     */
    public int getHits(int timestamp) {
        lock.lock();
        try {
            int total = 0;
            for (int i = 0; i < WINDOW_SECONDS; i++) {
                if (timestamp - times[i] < WINDOW_SECONDS && times[i] > 0) {
                    total += hits[i];
                }
            }
            return total;
        } finally {
            lock.unlock();
        }
    }
}
