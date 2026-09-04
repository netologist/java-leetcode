package com.leetcode.p03_stockprofit;

/**
 * LeetCode #121: Best Time to Buy and Sell Stock.
 * <p>
 * <b>FinTech Application:</b> High-frequency quantitative trading, maximum drawdown risk,
 * single-trade historical PnL backtesting (Citadel, Two Sigma).
 * <p>
 * <b>Time Complexity:</b> O(N) single pass.<br>
 * <b>Space Complexity:</b> O(1).
 */
public class StockMaxProfit {

    public record TradeWindow(
            int buyDay,
            int sellDay,
            int buyPrice,
            int sellPrice,
            int maxProfit
    ) {}

    public static TradeWindow calculateMaxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return new TradeWindow(0, 0, 0, 0, 0);
        }

        int minPrice = prices[0];
        int minDay = 0;

        int maxProfit = 0;
        int bestBuyDay = 0;
        int bestSellDay = 0;
        int bestBuyPrice = prices[0];
        int bestSellPrice = prices[0];

        for (int day = 1; day < prices.length; day++) {
            int currentPrice = prices[day];
            int profit = currentPrice - minPrice;

            if (profit > maxProfit) {
                maxProfit = profit;
                bestBuyDay = minDay;
                bestSellDay = day;
                bestBuyPrice = minPrice;
                bestSellPrice = currentPrice;
            }

            if (currentPrice < minPrice) {
                minPrice = currentPrice;
                minDay = day;
            }
        }

        return new TradeWindow(bestBuyDay, bestSellDay, bestBuyPrice, bestSellPrice, maxProfit);
    }
}
