package com.leetcode.p07_meetingrooms;

import com.leetcode.p04_mergeintervals.MergeIntervals.Interval;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * LeetCode #253: Meeting Rooms II.
 * <p>
 * <b>FinTech Application:</b> Real-time concurrent payment server worker allocation, core banking batch processor
 * capacity planning, server room / cloud instance peak sizing (Bloomberg, Stripe).
 * <p>
 * <b>Time Complexity:</b> O(N log N).<br>
 * <b>Space Complexity:</b> O(N).
 */
public class MeetingRoomsII {

    public static int minMeetingRooms(List<Interval> intervals) {
        if (intervals == null || intervals.isEmpty()) {
            return 0;
        }

        // 1. Sort intervals by start time ASC
        List<Interval> sorted = intervals.stream()
                .sorted(Comparator.comparingInt(Interval::start))
                .toList();

        // 2. Min-Heap tracking the earliest ending session
        PriorityQueue<Integer> endTimesMinHeap = new PriorityQueue<>();

        for (Interval interval : sorted) {
            if (!endTimesMinHeap.isEmpty() && endTimesMinHeap.peek() <= interval.start()) {
                // Room became available: reuse room by popping old end time
                endTimesMinHeap.poll();
            }
            endTimesMinHeap.add(interval.end());
        }

        // The size of the min-heap represents the peak concurrent rooms/servers required
        return endTimesMinHeap.size();
    }
}
