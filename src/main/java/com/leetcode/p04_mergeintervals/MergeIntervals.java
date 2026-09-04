package com.leetcode.p04_mergeintervals;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * LeetCode #56: Merge Intervals.
 * <p>
 * <b>FinTech Application:</b> Consolidating overlapping order book price tiers,
 * interest calculation periods, trading holiday session consolidation (Bloomberg, Stripe).
 * <p>
 * <b>Time Complexity:</b> O(N log N) sorting.<br>
 * <b>Space Complexity:</b> O(N).
 */
public class MergeIntervals {

    public record Interval(int start, int end) {
        public Interval {
            if (start > end) {
                throw new IllegalArgumentException("Start (" + start + ") cannot be greater than end (" + end + ")");
            }
        }
    }

    public static List<Interval> merge(List<Interval> intervals) {
        if (intervals == null || intervals.size() <= 1) {
            return intervals == null ? List.of() : List.copyOf(intervals);
        }

        // Sort by start time ASC using Java Streams & Comparator
        List<Interval> sorted = intervals.stream()
                .sorted(Comparator.comparingInt(Interval::start))
                .toList();

        List<Interval> merged = new ArrayList<>();
        Interval current = sorted.get(0);

        for (int i = 1; i < sorted.size(); i++) {
            Interval next = sorted.get(i);

            if (next.start() <= current.end()) {
                // Overlapping: expand current interval's end boundary
                current = new Interval(current.start(), Math.max(current.end(), next.end()));
            } else {
                merged.add(current);
                current = next;
            }
        }
        merged.add(current);

        return List.copyOf(merged);
    }
}
