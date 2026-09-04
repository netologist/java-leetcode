package com.leetcode.p17_coinchange;

import java.util.Arrays;

/**
 * LeetCode #322: Coin Change (Dynamic Programming).
 * <p>
 * <b>FinTech Application:</b> ATM cash cassette dispensing optimization (fewest bank notes),
 * cryptocurrency UTXO consolidation, payment denomination calculation (Citadel, Robinhood).
 * <p>
 * <b>Algorithm:</b> Bottom-Up 1D Dynamic Programming.<br>
 * <b>Time Complexity:</b> O(Amount * Number of Coins).<br>
 * <b>Space Complexity:</b> O(Amount).
 */
public class CoinChange {

    public static int coinChange(int[] coins, int amount) {
        if (amount < 0) return -1;
        if (amount == 0) return 0;
        if (coins == null || coins.length == 0) return -1;

        int[] dp = new int[amount + 1];
        // Fill with sentinel representing infinity
        Arrays.fill(dp, amount + 1);
        dp[0] = 0; // Base case: 0 coins needed for amount 0

        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i - coin >= 0) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

        return dp[amount] > amount ? -1 : dp[amount];
    }
}
