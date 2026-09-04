package com.leetcode.p09_reorganizestring;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * LeetCode #767: Reorganize String.
 * <p>
 * <b>FinTech Application:</b> Fair merchant API request scheduling, order routing symbol dispatching
 * (ensuring no two consecutive orders belong to the same stock ticker or merchant to prevent monopolization)
 * (Stripe, Citadel).
 * <p>
 * <b>Time Complexity:</b> O(N log A) where A is alphabet size (O(N) for 26 lowercase English letters).<br>
 * <b>Space Complexity:</b> O(A) = O(1).
 */
public class ReorganizeString {

    private record CharFreq(char ch, int count) {}

    public static String reorganize(String s) {
        if (s == null || s.length() <= 1) {
            return s == null ? "" : s;
        }

        // 1. Calculate character frequencies
        Map<Character, Integer> freqMap = new HashMap<>();
        for (char c : s.toCharArray()) {
            freqMap.put(c, freqMap.getOrDefault(c, 0) + 1);
        }

        // 2. Max-Heap ordered by frequency DESC
        PriorityQueue<CharFreq> maxHeap = new PriorityQueue<>((a, b) -> b.count() - a.count());
        for (var entry : freqMap.entrySet()) {
            maxHeap.add(new CharFreq(entry.getKey(), entry.getValue()));
        }

        // If the highest frequency exceeds (N + 1) / 2, pigeonhole principle makes it impossible
        if (maxHeap.peek().count() > (s.length() + 1) / 2) {
            return "";
        }

        StringBuilder result = new StringBuilder(s.length());

        // 3. Greedy placement using top two characters
        while (maxHeap.size() >= 2) {
            CharFreq first = maxHeap.poll();
            CharFreq second = maxHeap.poll();

            result.append(first.ch());
            result.append(second.ch());

            if (first.count() > 1) {
                maxHeap.add(new CharFreq(first.ch(), first.count() - 1));
            }
            if (second.count() > 1) {
                maxHeap.add(new CharFreq(second.ch(), second.count() - 1));
            }
        }

        // Append last remaining character if any
        if (!maxHeap.isEmpty()) {
            result.append(maxHeap.poll().ch());
        }

        return result.toString();
    }
}
