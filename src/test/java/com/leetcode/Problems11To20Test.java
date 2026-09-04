package com.leetcode;

import com.leetcode.p11_containerwater.ContainerWithMostWater;
import com.leetcode.p12_ratelimiter.TokenBucketRateLimiter;
import com.leetcode.p13_validparentheses.ValidParentheses;
import com.leetcode.p14_basiccalculator.BasicCalculatorII;
import com.leetcode.p15_kthlargest.KthLargestElement;
import com.leetcode.p16_numberofislands.NumberOfIslands;
import com.leetcode.p17_coinchange.CoinChange;
import com.leetcode.p18_groupanagrams.GroupAnagrams;
import com.leetcode.p19_hitcounter.HitCounter;
import com.leetcode.p20_accountsmerge.AccountsMerge;
import com.leetcode.p20_accountsmerge.AccountsMerge.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Problems11To20Test {

    @Test
    @DisplayName("P11: Container With Most Water finds maximum area")
    void testContainerWithMostWater() {
        int[] height = {1, 8, 6, 2, 5, 4, 8, 3, 7};
        var res = ContainerWithMostWater.maxArea(height);
        assertThat(res.maxArea()).isEqualTo(49);
    }

    @Test
    @DisplayName("P12: Token Bucket Rate Limiter enforces capacity bounds")
    void testTokenBucketRateLimiter() {
        TokenBucketRateLimiter limiter = new TokenBucketRateLimiter(2, 1.0);
        assertThat(limiter.tryAcquire()).isTrue();
        assertThat(limiter.tryAcquire()).isTrue();
        assertThat(limiter.tryAcquire()).isFalse(); // Capacity 2 exhausted
    }

    @Test
    @DisplayName("P13: Valid Parentheses validates syntax matching")
    void testValidParentheses() {
        assertThat(ValidParentheses.isValid("()[]{}")).isTrue();
        assertThat(ValidParentheses.isValid("(]")).isFalse();
        assertThat(ValidParentheses.isValid("([)]")).isFalse();
        assertThat(ValidParentheses.isValid("{[]}")).isTrue();
    }

    @Test
    @DisplayName("P14: Basic Calculator II respects operator precedence")
    void testBasicCalculatorII() {
        assertThat(BasicCalculatorII.calculate("3+2*2")).isEqualTo(7);
        assertThat(BasicCalculatorII.calculate(" 3/2 ")).isEqualTo(1);
        assertThat(BasicCalculatorII.calculate(" 3+5 / 2 ")).isEqualTo(5);
    }

    @Test
    @DisplayName("P15: Kth Largest Element in an Array finds kth rank using Quickselect")
    void testKthLargestElement() {
        int[] nums = {3, 2, 1, 5, 6, 4};
        assertThat(KthLargestElement.findKthLargest(nums, 2)).isEqualTo(5);
    }

    @Test
    @DisplayName("P16: Number of Islands counts connected land components")
    void testNumberOfIslands() {
        char[][] grid = {
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}
        };
        assertThat(NumberOfIslands.numIslands(grid)).isEqualTo(3);
    }

    @Test
    @DisplayName("P17: Coin Change computes minimum coins with DP")
    void testCoinChange() {
        int[] coins = {1, 2, 5};
        assertThat(CoinChange.coinChange(coins, 11)).isEqualTo(3); // 5 + 5 + 1
        assertThat(CoinChange.coinChange(new int[]{2}, 3)).isEqualTo(-1);
    }

    @Test
    @DisplayName("P18: Group Anagrams groups strings by character frequency signature")
    void testGroupAnagrams() {
        String[] strs = {"eat", "tea", "tan", "ate", "nat", "bat"};
        var groups = GroupAnagrams.groupAnagrams(strs);
        assertThat(groups).hasSize(3);
    }

    @Test
    @DisplayName("P19: Hit Counter counts hits in rolling 300 second window")
    void testHitCounter() {
        HitCounter counter = new HitCounter();
        counter.hit(1);
        counter.hit(2);
        counter.hit(3);
        assertThat(counter.getHits(4)).isEqualTo(3);

        counter.hit(300);
        assertThat(counter.getHits(300)).isEqualTo(4);

        // At timestamp 301, hit at timestamp 1 has rolled out of 300s window!
        assertThat(counter.getHits(301)).isEqualTo(3);
    }

    @Test
    @DisplayName("P20: Accounts Merge merges accounts using Union-Find")
    void testAccountsMerge() {
        List<Account> accounts = List.of(
                new Account("John", List.of("johnsmith@mail.com", "john_newyork@mail.com")),
                new Account("John", List.of("johnsmith@mail.com", "john00@mail.com")),
                new Account("Mary", List.of("mary@mail.com")),
                new Account("John", List.of("johnnybravo@mail.com"))
        );

        var merged = AccountsMerge.accountsMerge(accounts);
        assertThat(merged).hasSize(3);
    }
}
