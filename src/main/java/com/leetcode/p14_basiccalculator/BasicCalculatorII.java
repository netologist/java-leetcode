package com.leetcode.p14_basiccalculator;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * LeetCode #227: Basic Calculator II.
 * <p>
 * <b>FinTech Application:</b> Dynamic fee & interest calculation rule engine, arithmetic expression evaluator
 * respecting operator precedence without using eval() or third-party engines (Bloomberg).
 * <p>
 * <b>Time Complexity:</b> O(N) single-pass.<br>
 * <b>Space Complexity:</b> O(N).
 */
public class BasicCalculatorII {

    public static int calculate(String s) {
        if (s == null || s.isBlank()) {
            return 0;
        }

        Deque<Integer> stack = new ArrayDeque<>();
        int currentNumber = 0;
        char operation = '+';
        int length = s.length();

        for (int i = 0; i < length; i++) {
            char currentChar = s.charAt(i);

            if (Character.isDigit(currentChar)) {
                currentNumber = (currentNumber * 10) + (currentChar - '0');
            }

            if ((!Character.isDigit(currentChar) && currentChar != ' ') || i == length - 1) {
                switch (operation) {
                    case '+' -> stack.push(currentNumber);
                    case '-' -> stack.push(-currentNumber);
                    case '*' -> stack.push(stack.pop() * currentNumber);
                    case '/' -> stack.push(stack.pop() / currentNumber);
                }
                operation = currentChar;
                currentNumber = 0;
            }
        }

        int result = 0;
        while (!stack.isEmpty()) {
            result += stack.pop();
        }
        return result;
    }
}
