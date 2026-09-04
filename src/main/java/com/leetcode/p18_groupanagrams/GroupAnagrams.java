package com.leetcode.p18_groupanagrams;

import java.util.*;
import java.util.stream.Collectors;

/**
 * LeetCode #49: Group Anagrams.
 * <p>
 * <b>FinTech Application:</b> Merchant name normalization on bank statements (e.g. "AMZN MKTP", "AMZN", "AMAZON"),
 * transaction memo entity canonicalization (Stripe, Plaid).
 * <p>
 * <b>Algorithm:</b> Character frequency signature hashing using Java Streams.<br>
 * <b>Time Complexity:</b> O(N * K) where N is number of strings and K is max string length.<br>
 * <b>Space Complexity:</b> O(N * K).
 */
public class GroupAnagrams {

    public static List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) {
            return List.of();
        }

        return Arrays.stream(strs)
                .collect(Collectors.groupingBy(GroupAnagrams::buildFrequencyKey, LinkedHashMap::new, Collectors.toList()))
                .values()
                .stream()
                .toList();
    }

    private static String buildFrequencyKey(String s) {
        int[] counts = new int[26];
        for (char c : s.toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                counts[c - 'a']++;
            }
        }

        StringBuilder sb = new StringBuilder(52);
        for (int i = 0; i < 26; i++) {
            if (counts[i] > 0) {
                sb.append((char) ('a' + i)).append(counts[i]);
            }
        }
        return sb.toString();
    }
}
