package com.leetcode.p08_subarraysum;

import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode #560: Subarray Sum Equals K.
 * <p>
 * <b>FinTech Application:</b> Transaction window reconciliation, continuous rolling trade value detection,
 * Anti-Money Laundering (AML) suspicious structuring threshold identification (Stripe, Citadel).
 * <p>
 * <b>Algorithm:</b> Prefix Sum + Frequency HashMap.<br>
 * <b>Time Complexity:</b> O(N) single-pass.<br>
 * <b>Space Complexity:</b> O(N).
 */
public class SubarraySumK {

    public static int subarraySum(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        // PrefixSum -> Frequency count
        Map<Integer, Integer> prefixSumCounts = new HashMap<>();
        prefixSumCounts.put(0, 1); // Base case: prefix sum of 0 appears once initially

        int cumulativeSum = 0;
        int totalSubarrays = 0;

        for (int num : nums) {
            cumulativeSum += num;

            // If (cumulativeSum - k) exists in prefix map, we found valid subarrays
            int complement = cumulativeSum - k;
            totalSubarrays += prefixSumCounts.getOrDefault(complement, 0);

            // Record current cumulative sum in frequency map
            prefixSumCounts.put(cumulativeSum, prefixSumCounts.getOrDefault(cumulativeSum, 0) + 1);
        }

        return totalSubarrays;
    }
}
