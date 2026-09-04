package com.leetcode.p05_insertinterval;

import com.leetcode.p04_mergeintervals.MergeIntervals.Interval;

import java.util.ArrayList;
import java.util.List;

/**
 * LeetCode #57: Insert Interval.
 * <p>
 * <b>FinTech Application:</b> Dynamic fee tier adjustments, dynamic exchange trading hours,
 * settlement window insertion into already-sorted ledger schedules (Citadel, Robinhood).
 * <p>
 * <b>Time Complexity:</b> O(N) single-pass.<br>
 * <b>Space Complexity:</b> O(N).
 */
public class InsertInterval {

    public static List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        if (newInterval == null) {
            return intervals == null ? List.of() : List.copyOf(intervals);
        }
        if (intervals == null || intervals.isEmpty()) {
            return List.of(newInterval);
        }

        List<Interval> result = new ArrayList<>();
        int i = 0;
        int n = intervals.size();

        // 1. Add all intervals ending before newInterval starts (no overlap)
        while (i < n && intervals.get(i).end() < newInterval.start()) {
            result.add(intervals.get(i));
            i++;
        }

        // 2. Merge all overlapping intervals with newInterval
        int mergedStart = newInterval.start();
        int mergedEnd = newInterval.end();

        while (i < n && intervals.get(i).start() <= mergedEnd) {
            mergedStart = Math.min(mergedStart, intervals.get(i).start());
            mergedEnd = Math.max(mergedEnd, intervals.get(i).end());
            i++;
        }
        result.add(new Interval(mergedStart, mergedEnd));

        // 3. Add all remaining intervals starting after mergedInterval ends
        while (i < n) {
            result.add(intervals.get(i));
            i++;
        }

        return List.copyOf(result);
    }
}
