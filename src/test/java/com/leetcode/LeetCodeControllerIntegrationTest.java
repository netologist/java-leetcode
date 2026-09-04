package com.leetcode;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leetcode.api.LeetCodeController;
import com.leetcode.p04_mergeintervals.MergeIntervals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LeetCodeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("REST POST /twosum computes target indices")
    void testTwoSumEndpoint() throws Exception {
        var req = new LeetCodeController.TwoSumRequest(new int[]{2, 7, 11, 15}, 9);

        mockMvc.perform(post("/api/v1/twosum")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.index1", is(0)))
                .andExpect(jsonPath("$.index2", is(1)));
    }

    @Test
    @DisplayName("REST POST /stock/max-profit calculates optimal trade")
    void testStockProfitEndpoint() throws Exception {
        var req = new LeetCodeController.StockProfitRequest(new int[]{7, 1, 5, 3, 6, 4});

        mockMvc.perform(post("/api/v1/stock/max-profit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.maxProfit", is(5)))
                .andExpect(jsonPath("$.buyPrice", is(1)))
                .andExpect(jsonPath("$.sellPrice", is(6)));
    }

    @Test
    @DisplayName("REST POST /intervals/merge merges intervals")
    void testMergeIntervalsEndpoint() throws Exception {
        var req = new LeetCodeController.MergeIntervalsRequest(List.of(
                new MergeIntervals.Interval(1, 4),
                new MergeIntervals.Interval(3, 7)
        ));

        mockMvc.perform(post("/api/v1/intervals/merge")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].start", is(1)))
                .andExpect(jsonPath("$[0].end", is(7)));
    }

    @Test
    @DisplayName("REST HitCounter & RateLimiter endpoints")
    void testMetricsAndRateLimitEndpoints() throws Exception {
        mockMvc.perform(post("/api/v1/metrics/hit?timestamp=100"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("RECORDED")));

        mockMvc.perform(get("/api/v1/metrics/hits?timestamp=105"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hitsLast300Seconds", is(1)));

        mockMvc.perform(get("/api/v1/ratelimit/try-acquire"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.permitted", is(true)));
    }
}
