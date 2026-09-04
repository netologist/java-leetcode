package com.leetcode.p10_topkfrequent;

import java.util.*;
import java.util.stream.Collectors;

/**
 * LeetCode #347: Top K Frequent Elements.
 * <p>
 * <b>FinTech Application:</b> Finding most actively traded equities during market open,
 * identifying high-velocity fraud IP hotspots, card terminal failure clustering (Bloomberg, Stripe).
 * <p>
 * <b>Time Complexity:</b> O(N) using Bucket Sort.<br>
 * <b>Space Complexity:</b> O(N).
 */
public class TopKFrequentElements {

    public static List<Integer> topKFrequent(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0) {
            return List.of();
        }

        // 1. Calculate frequencies using Java Streams
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        // 2. Bucket Sort: Index in bucket represents frequency
        List<Integer>[] buckets = new List[nums.length + 1];
        for (var entry : freqMap.entrySet()) {
            int freq = entry.getValue();
            if (buckets[freq] == null) {
                buckets[freq] = new ArrayList<>();
            }
            buckets[freq].add(entry.getKey());
        }

        // 3. Collect top k elements from highest frequency buckets down to 1
        List<Integer> result = new ArrayList<>(k);
        for (int i = buckets.length - 1; i >= 0 && result.size() < k; i--) {
            if (buckets[i] != null) {
                for (int val : buckets[i]) {
                    result.add(val);
                    if (result.size() == k) {
                        break;
                    }
                }
            }
        }

        return List.copyOf(result);
    }
}
