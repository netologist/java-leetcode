package com.leetcode.p16_numberofislands;

/**
 * LeetCode #200: Number of Islands.
 * <p>
 * <b>FinTech Application:</b> Fraud ring detection, collusive money laundering network clustering,
 * illicit device fingerprint graph component counting (Bloomberg, Stripe).
 * <p>
 * <b>Algorithm:</b> Depth-First Search (DFS) / Connected Components.<br>
 * <b>Time Complexity:</b> O(M x N).<br>
 * <b>Space Complexity:</b> O(M x N) recursion call stack.
 */
public class NumberOfIslands {

    public static int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;
        int islandsCount = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (grid[r][c] == '1') {
                    islandsCount++;
                    dfs(grid, r, c, rows, cols);
                }
            }
        }

        return islandsCount;
    }

    private static void dfs(char[][] grid, int r, int c, int rows, int cols) {
        if (r < 0 || r >= rows || c < 0 || c >= cols || grid[r][c] != '1') {
            return;
        }

        // Sink the island to mark as visited
        grid[r][c] = '0';

        dfs(grid, r + 1, c, rows, cols); // down
        dfs(grid, r - 1, c, rows, cols); // up
        dfs(grid, r, c + 1, rows, cols); // right
        dfs(grid, r, c - 1, rows, cols); // left
    }
}
