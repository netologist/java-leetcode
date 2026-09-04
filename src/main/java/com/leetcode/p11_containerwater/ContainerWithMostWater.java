package com.leetcode.p11_containerwater;

/**
 * LeetCode #11: Container With Most Water.
 * <p>
 * <b>FinTech Application:</b> Liquidity depth maximization, optimal market-maker bid-ask spread duration,
 * cross-exchange latency arbitrage window sizing (Robinhood, Citadel).
 * <p>
 * <b>Algorithm:</b> Two-Pointer Greedy.<br>
 * <b>Time Complexity:</b> O(N) single-pass.<br>
 * <b>Space Complexity:</b> O(1).
 */
public class ContainerWithMostWater {

    public record MaxAreaResult(int leftIndex, int rightIndex, int maxArea) {}

    public static MaxAreaResult maxArea(int[] height) {
        if (height == null || height.length < 2) {
            return new MaxAreaResult(0, 0, 0);
        }

        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;
        int bestLeft = 0;
        int bestRight = 0;

        while (left < right) {
            int width = right - left;
            int minHeight = Math.min(height[left], height[right]);
            int currentArea = width * minHeight;

            if (currentArea > maxArea) {
                maxArea = currentArea;
                bestLeft = left;
                bestRight = right;
            }

            // Greedily move the pointer pointing to the shorter vertical bar
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }

        return new MaxAreaResult(bestLeft, bestRight, maxArea);
    }
}
