package com.leetcode;

import com.leetcode.p01_lrucache.LRUCache;
import com.leetcode.p02_twosum.TwoSum;
import com.leetcode.p03_stockprofit.StockMaxProfit;
import com.leetcode.p04_mergeintervals.MergeIntervals;
import com.leetcode.p04_mergeintervals.MergeIntervals.Interval;
import com.leetcode.p05_insertinterval.InsertInterval;
import com.leetcode.p06_randomizedset.RandomizedSet;
import com.leetcode.p07_meetingrooms.MeetingRoomsII;
import com.leetcode.p08_subarraysum.SubarraySumK;
import com.leetcode.p09_reorganizestring.ReorganizeString;
import com.leetcode.p10_topkfrequent.TopKFrequentElements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Problems01To10Test {

    @Test
    @DisplayName("P01: LRU Cache correctly evicts least recently used items")
    void testLRUCache() {
        LRUCache<Integer, Integer> cache = new LRUCache<>(2);
        cache.put(1, 100);
        cache.put(2, 200);

        assertThat(cache.get(1)).isEqualTo(100); // Access key 1 -> key 2 becomes LRU

        cache.put(3, 300); // Evicts key 2!
        assertThat(cache.get(2)).isNull();
        assertThat(cache.get(3)).isEqualTo(300);
        assertThat(cache.get(1)).isEqualTo(100);
    }

    @Test
    @DisplayName("P02: Two Sum identifies complement indices")
    void testTwoSum() {
        int[] nums = {2, 7, 11, 15};
        var res = TwoSum.findTwoSum(nums, 9).orElseThrow();
        assertThat(res.index1()).isEqualTo(0);
        assertThat(res.index2()).isEqualTo(1);
    }

    @Test
    @DisplayName("P03: Stock Max Profit finds optimal buy and sell days")
    void testStockMaxProfit() {
        int[] prices = {7, 1, 5, 3, 6, 4};
        var window = StockMaxProfit.calculateMaxProfit(prices);
        assertThat(window.maxProfit()).isEqualTo(5); // Buy at 1 (day 1), sell at 6 (day 4)
        assertThat(window.buyPrice()).isEqualTo(1);
        assertThat(window.sellPrice()).isEqualTo(6);
    }

    @Test
    @DisplayName("P04: Merge Intervals merges overlapping intervals")
    void testMergeIntervals() {
        List<Interval> input = List.of(
                new Interval(1, 3),
                new Interval(2, 6),
                new Interval(8, 10),
                new Interval(15, 18)
        );
        var merged = MergeIntervals.merge(input);
        assertThat(merged).containsExactly(
                new Interval(1, 6),
                new Interval(8, 10),
                new Interval(15, 18)
        );
    }

    @Test
    @DisplayName("P05: Insert Interval inserts and merges cleanly")
    void testInsertInterval() {
        List<Interval> intervals = List.of(new Interval(1, 3), new Interval(6, 9));
        var result = InsertInterval.insert(intervals, new Interval(2, 5));
        assertThat(result).containsExactly(new Interval(1, 5), new Interval(6, 9));
    }

    @Test
    @DisplayName("P06: RandomizedSet performs O(1) insert, remove, and getRandom")
    void testRandomizedSet() {
        RandomizedSet<Integer> set = new RandomizedSet<>();
        assertThat(set.insert(10)).isTrue();
        assertThat(set.insert(10)).isFalse();
        assertThat(set.insert(20)).isTrue();
        assertThat(set.size()).isEqualTo(2);

        assertThat(set.remove(10)).isTrue();
        assertThat(set.remove(10)).isFalse();
        assertThat(set.getRandom()).isEqualTo(20);
    }

    @Test
    @DisplayName("P07: Meeting Rooms II computes concurrent peak rooms")
    void testMeetingRoomsII() {
        List<Interval> meetings = List.of(
                new Interval(0, 30),
                new Interval(5, 10),
                new Interval(15, 20)
        );
        assertThat(MeetingRoomsII.minMeetingRooms(meetings)).isEqualTo(2);
    }

    @Test
    @DisplayName("P08: Subarray Sum Equals K counts target sum subarrays")
    void testSubarraySumK() {
        int[] nums = {1, 1, 1};
        assertThat(SubarraySumK.subarraySum(nums, 2)).isEqualTo(2);

        int[] numsWithNegatives = {1, -1, 0};
        assertThat(SubarraySumK.subarraySum(numsWithNegatives, 0)).isEqualTo(3);
    }

    @Test
    @DisplayName("P09: Reorganize String reorders characters without adjacent duplicates")
    void testReorganizeString() {
        String res = ReorganizeString.reorganize("aab");
        assertThat(res).isEqualTo("aba");

        // Impossible case
        assertThat(ReorganizeString.reorganize("aaab")).isEmpty();
    }

    @Test
    @DisplayName("P10: Top K Frequent Elements returns most frequent items")
    void testTopKFrequent() {
        int[] nums = {1, 1, 1, 2, 2, 3};
        var top2 = TopKFrequentElements.topKFrequent(nums, 2);
        assertThat(top2).containsExactlyInAnyOrder(1, 2);
    }
}
