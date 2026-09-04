package com.leetcode.api;

import com.leetcode.p02_twosum.TwoSum;
import com.leetcode.p03_stockprofit.StockMaxProfit;
import com.leetcode.p04_mergeintervals.MergeIntervals;
import com.leetcode.p12_ratelimiter.TokenBucketRateLimiter;
import com.leetcode.p19_hitcounter.HitCounter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class LeetCodeController {

    private final HitCounter hitCounter = new HitCounter();
    private final TokenBucketRateLimiter rateLimiter = new TokenBucketRateLimiter(100, 10.0);

    public record TwoSumRequest(int[] nums, int target) {}
    public record StockProfitRequest(int[] prices) {}
    public record MergeIntervalsRequest(List<MergeIntervals.Interval> intervals) {}

    @PostMapping("/twosum")
    public ResponseEntity<?> solveTwoSum(@RequestBody TwoSumRequest req) {
        return TwoSum.findTwoSum(req.nums(), req.target())
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/stock/max-profit")
    public ResponseEntity<StockMaxProfit.TradeWindow> solveStockProfit(@RequestBody StockProfitRequest req) {
        return ResponseEntity.ok(StockMaxProfit.calculateMaxProfit(req.prices()));
    }

    @PostMapping("/intervals/merge")
    public ResponseEntity<List<MergeIntervals.Interval>> mergeIntervals(@RequestBody MergeIntervalsRequest req) {
        return ResponseEntity.ok(MergeIntervals.merge(req.intervals()));
    }

    @PostMapping("/metrics/hit")
    public ResponseEntity<Map<String, String>> recordHit(@RequestParam(required = false) Integer timestamp) {
        int ts = (timestamp != null) ? timestamp : (int) (System.currentTimeMillis() / 1000);
        hitCounter.hit(ts);
        return ResponseEntity.ok(Map.of("status", "RECORDED", "timestamp", String.valueOf(ts)));
    }

    @GetMapping("/metrics/hits")
    public ResponseEntity<Map<String, Integer>> getHits(@RequestParam(required = false) Integer timestamp) {
        int ts = (timestamp != null) ? timestamp : (int) (System.currentTimeMillis() / 1000);
        return ResponseEntity.ok(Map.of("hitsLast300Seconds", hitCounter.getHits(ts)));
    }

    @GetMapping("/ratelimit/try-acquire")
    public ResponseEntity<Map<String, Object>> tryAcquireRateLimit() {
        boolean acquired = rateLimiter.tryAcquire();
        return ResponseEntity.ok(Map.of(
                "permitted", acquired,
                "availableTokens", rateLimiter.getAvailableTokens()
        ));
    }
}
