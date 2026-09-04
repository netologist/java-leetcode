package com.leetcode.p13_validparentheses;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

/**
 * LeetCode #20: Valid Parentheses.
 * <p>
 * <b>FinTech Application:</b> FIX / SWIFT / ISO-8583 message syntax parsing, nested financial formula scoping,
 * smart contract clause delimiter validation (Stripe, Plaid).
 * <p>
 * <b>Algorithm:</b> Stack (using {@link ArrayDeque} for high-performance memory efficiency).<br>
 * <b>Time Complexity:</b> O(N).<br>
 * <b>Space Complexity:</b> O(N).
 */
public class ValidParentheses {

    private static final Map<Character, Character> MATCHING_PAIRS = Map.of(
            ')', '(',
            '}', '{',
            ']', '['
    );

    public static boolean isValid(String s) {
        if (s == null || s.length() % 2 != 0) {
            return false; // Odd length strings can never be fully balanced
        }

        Deque<Character> stack = new ArrayDeque<>(s.length());

        for (char c : s.toCharArray()) {
            if (MATCHING_PAIRS.containsKey(c)) {
                // Closing bracket: must match top of stack
                if (stack.isEmpty() || stack.pop() != MATCHING_PAIRS.get(c)) {
                    return false;
                }
            } else if (MATCHING_PAIRS.containsValue(c)) {
                // Opening bracket: push onto stack
                stack.push(c);
            }
        }

        return stack.isEmpty();
    }
}
