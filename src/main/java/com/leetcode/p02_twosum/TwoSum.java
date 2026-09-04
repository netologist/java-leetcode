package com.leetcode.p02_twosum;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * LeetCode #1: Two Sum.
 * <p>
 * <b>FinTech Application:</b> Double-entry general ledger balancing, matching debit/credit entries,
 * FX currency pair offset matching (Stripe, Plaid).
 * <p>
 * <b>Time Complexity:</b> O(N) single-pass hash lookup.<br>
 * <b>Space Complexity:</b> O(N).
 */
public class TwoSum {

    public record TwoSumResult(int index1, int index2) {}

    public static Optional<TwoSumResult> findTwoSum(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return Optional.empty();
        }

        Map<Integer, Integer> complementMap = new HashMap<>(); // Complement -> Index

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            Integer complementIndex = complementMap.get(complement);

            if (complementIndex != null) {
                return Optional.of(new TwoSumResult(complementIndex, i));
            }

            complementMap.put(nums[i], i);
        }

        return Optional.empty();
    }
}
