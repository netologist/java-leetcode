package com.leetcode.p15_kthlargest;

import java.util.Random;

/**
 * LeetCode #215: Kth Largest Element in an Array.
 * <p>
 * <b>FinTech Application:</b> Real-time SLA latency percentile computation (p99, p95, p50),
 * dark pool crossing volume order thresholding (Citadel, Two Sigma).
 * <p>
 * <b>Algorithm:</b> Quickselect (Hoare's Selection Algorithm with randomized pivot).<br>
 * <b>Time Complexity:</b> O(N) average, O(N^2) worst-case.<br>
 * <b>Space Complexity:</b> O(1) in-place.
 */
public class KthLargestElement {

    private static final Random RANDOM = new Random();

    public static int findKthLargest(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k <= 0 || k > nums.length) {
            throw new IllegalArgumentException("Invalid input array or k");
        }

        // The kth largest element is at index (nums.length - k) in sorted order
        int targetIndex = nums.length - k;
        return quickSelect(nums, 0, nums.length - 1, targetIndex);
    }

    private static int quickSelect(int[] nums, int left, int right, int targetIndex) {
        if (left == right) {
            return nums[left];
        }

        int pivotIndex = left + RANDOM.nextInt(right - left + 1);
        int finalPivotIndex = partition(nums, left, right, pivotIndex);

        if (finalPivotIndex == targetIndex) {
            return nums[finalPivotIndex];
        } else if (finalPivotIndex < targetIndex) {
            return quickSelect(nums, finalPivotIndex + 1, right, targetIndex);
        } else {
            return quickSelect(nums, left, finalPivotIndex - 1, targetIndex);
        }
    }

    private static int partition(int[] nums, int left, int right, int pivotIndex) {
        int pivotValue = nums[pivotIndex];
        swap(nums, pivotIndex, right); // Move pivot to end

        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (nums[i] < pivotValue) {
                swap(nums, storeIndex, i);
                storeIndex++;
            }
        }
        swap(nums, storeIndex, right); // Move pivot to final position
        return storeIndex;
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
