# ⚡ java-leetcode: FinTech & Systems Live Coding Suite in Modern Java 21+ & Spring Boot 3.4

A curated, production-grade reference implementation of **20 mission-critical algorithmic and systems coding problems** frequently featured in technical interviews and live coding assessments at premier FinTech, Quantitative Trading, and Infrastructure companies (**Stripe, Robinhood, Citadel, Bloomberg, Two Sigma, Brex, Revolut, Plaid, Coinbase, Adyen**).

This project translates and reimagines the Go [`leetcodeX/`](../leetcodeX/) suite into idiomatic **Modern Java (Java 21 / 25+)** and **Spring Boot 3.4.x**, strictly utilizing **Java Records**, **Streams API**, **Virtual Threads (Project Loom)**, and **Reentrant Locks**.

---

## 📑 Table of Contents

1. [Architectural Overview & Highlights](#architectural-overview--highlights)
2. [Problem Index & FinTech Domain Mapping](#problem-index--fintech-domain-mapping)
3. [Deep Dive into Modern Java 21+ Architectural Patterns](#deep-dive-into-modern-java-21-architectural-patterns)
   - [Java Records for Immutability & Financial Invariants](#1-java-records-for-immutability--financial-invariants)
   - [Java Streams API for High-Throughput Aggregations](#2-java-streams-api-for-high-throughput-aggregations)
   - [Java 21 Virtual Threads (Project Loom) for Concurrent Systems](#3-java-21-virtual-threads-project-loom-for-concurrent-systems)
4. [REST API Endpoints Reference](#rest-api-endpoints-reference)
5. [Running the Application & Tests](#running-the-application--tests)

---

## Architectural Overview & Highlights

- **Java Records:** Immutable models (`record Interval`, `record Account`, `record TradeWindow`, `record TwoSumResult`, `record MaxAreaResult`) eliminating mutable state bugs and boilerplates.
- **Java Streams API:** Declarative data transformations, frequency grouping (`Collectors.groupingBy`), sorting, and multi-field interval slicing.
- **Virtual Threads (Project Loom):** Enabled via `spring.threads.virtual.enabled=true`. The test suite verifies 1,000 concurrent Virtual Threads executing against `LRUCache`, `HitCounter`, and `TokenBucketRateLimiter` with zero data races.
- **Locking & Concurrency:** Concurrency-critical components utilize `ReentrantReadWriteLock` and `ReentrantLock` ensuring carrier threads are unpinned during blocking operations.

---

## 🧭 Problem Index & FinTech Domain Mapping

| # | Problem | LeetCode | Difficulty | Real-World FinTech Domain & Target Companies | Time | Space | Package Path |
|:---:|:---|:---:|:---:|:---|:---:|:---:|:---|
| **01** | **LRU Cache** | #146 | Medium | *Market Tick Caching, Idempotency Keys, Session Caches* (Stripe, Robinhood, Citadel) | $O(1)$ | $O(C)$ | [`p01_lrucache`](src/main/java/com/leetcode/p01_lrucache/LRUCache.java) |
| **02** | **Two Sum** | #1 | Easy | *Double-Entry Ledger Balancing, FX Currency Pair Matching* (Stripe, Plaid) | $O(N)$ | $O(N)$ | [`p02_twosum`](src/main/java/com/leetcode/p02_twosum/TwoSum.java) |
| **03** | **Stock Trading Max Profit** | #121 | Easy | *Algorithmic Trading, Maximum Drawdown, Real-Time PnL* (Citadel, Two Sigma) | $O(N)$ | $O(1)$ | [`p03_stockprofit`](src/main/java/com/leetcode/p03_stockprofit/StockMaxProfit.java) |
| **04** | **Merge Intervals** | #56 | Medium | *Order Book Price Tiers, Interest Calculation Periods* (Stripe, Bloomberg) | $O(N \log N)$ | $O(N)$ | [`p04_mergeintervals`](src/main/java/com/leetcode/p04_mergeintervals/MergeIntervals.java) |
| **05** | **Insert Interval** | #57 | Medium | *Dynamic Fee Tier Adjustments, Settlement Window Merging* (Citadel, Robinhood) | $O(N)$ | $O(N)$ | [`p05_insertinterval`](src/main/java/com/leetcode/p05_insertinterval/InsertInterval.java) |
| **06** | **RandomizedSet $O(1)$** | #380 | Medium | *Liquidity Pool Sampling, Real-Time Fraud Audit Inspection* (Robinhood) | $O(1)$ | $O(N)$ | [`p06_randomizedset`](src/main/java/com/leetcode/p06_randomizedset/RandomizedSet.java) |
| **07** | **Meeting Rooms II** | #253 | Medium | *Concurrent Payment Server Allocation, Core Banking Scalability* (Bloomberg, Stripe) | $O(N \log N)$ | $O(N)$ | [`p07_meetingrooms`](src/main/java/com/leetcode/p07_meetingrooms/MeetingRoomsII.java) |
| **08** | **Subarray Sum Equals K** | #560 | Medium | *Transaction Window Reconciliation, Anti-Money Laundering (AML)* (Stripe, Citadel) | $O(N)$ | $O(N)$ | [`p08_subarraysum`](src/main/java/com/leetcode/p08_subarraysum/SubarraySumK.java) |
| **09** | **Reorganize String** | #767 | Medium | *Merchant Rate-Limit Scheduling, Symbol Dispatcher* (Stripe, Citadel) | $O(N)$ | $O(1)$ | [`p09_reorganizestring`](src/main/java/com/leetcode/p09_reorganizestring/ReorganizeString.java) |
| **10** | **Top K Frequent Elements** | #347 | Medium | *Most Actively Traded Equities, High-Velocity Fraud Hotspots* (Bloomberg) | $O(N)$ | $O(N)$ | [`p10_topkfrequent`](src/main/java/com/leetcode/p10_topkfrequent/TopKFrequentElements.java) |
| **11** | **Container With Most Water** | #11 | Medium | *Liquidity Depth Maximization, Spread Arbitrage Windows* (Robinhood) | $O(N)$ | $O(1)$ | [`p11_containerwater`](src/main/java/com/leetcode/p11_containerwater/ContainerWithMostWater.java) |
| **12** | **Token Bucket Rate Limiter** | Custom | Medium | *Stripe/Revolut Public API Throttling, Burst Capacity Control* (Stripe, Revolut) | $O(1)$ | $O(1)$ | [`p12_ratelimiter`](src/main/java/com/leetcode/p12_ratelimiter/TokenBucketRateLimiter.java) |
| **13** | **Valid Parentheses** | #20 | Easy | *FIX / SWIFT / ISO-8583 Message Syntax Parsing* (Stripe, Plaid) | $O(N)$ | $O(N)$ | [`p13_validparentheses`](src/main/java/com/leetcode/p13_validparentheses/ValidParentheses.java) |
| **14** | **Basic Calculator II** | #227 | Medium | *Fee & Interest Rule Engine, Arithmetic Precedence* (Bloomberg) | $O(N)$ | $O(N)$ | [`p14_basiccalculator`](src/main/java/com/leetcode/p14_basiccalculator/BasicCalculatorII.java) |
| **15** | **Kth Largest Element in Array** | #215 | Medium | *Latency Percentile Computations (p99), Dark Pool Crossing* (Citadel) | $O(N)$ | $O(1)$ | [`p15_kthlargest`](src/main/java/com/leetcode/p15_kthlargest/KthLargestElement.java) |
| **16** | **Number of Islands** | #200 | Medium | *Fraud Ring Detection, Connected Account Clustering* (Bloomberg, Stripe) | $O(M \times N)$ | $O(M \times N)$ | [`p16_numberofislands`](src/main/java/com/leetcode/p16_numberofislands/NumberOfIslands.java) |
| **17** | **Coin Change (DP)** | #322 | Medium | *ATM Cash Dispensing Optimization, Crypto UTXO Selection* (Citadel, Robinhood) | $O(A \times C)$ | $O(A)$ | [`p17_coinchange`](src/main/java/com/leetcode/p17_coinchange/CoinChange.java) |
| **18** | **Group Anagrams** | #49 | Medium | *Merchant Name Normalization, Bank Statement Enrichment* (Stripe, Plaid) | $O(N \times K)$ | $O(N \times K)$ | [`p18_groupanagrams`](src/main/java/com/leetcode/p18_groupanagrams/GroupAnagrams.java) |
| **19** | **Design Hit Counter** | #362 | Medium | *Real-Time Transaction Per Second (TPS), Error Rate Breakers* (Stripe, Datadog) | $O(1)$ | $O(1)$ | [`p19_hitcounter`](src/main/java/com/leetcode/p19_hitcounter/HitCounter.java) |
| **20** | **Accounts Merge (Union-Find)** | #721 | Medium | *KYC Single Customer View, Sybil & Multi-Account Detection* (Plaid, Stripe) | $O(NK \log NK)$ | $O(NK)$ | [`p20_accountsmerge`](src/main/java/com/leetcode/p20_accountsmerge/AccountsMerge.java) |

---

## Deep Dive into Modern Java 21+ Architectural Patterns

### 1. Java Records for Immutability & Financial Invariants

```java
// Immutable Interval model with range invariant validation
public record Interval(int start, int end) {
    public Interval {
        if (start > end) {
            throw new IllegalArgumentException("Start cannot be greater than end");
        }
    }
}
```

```java
// Algorithmic Trading Window Result
public record TradeWindow(
    int buyDay,
    int sellDay,
    int buyPrice,
    int sellPrice,
    int maxProfit
) {}
```

---

### 2. Java Streams API for High-Throughput Aggregations

```java
// Grouping bank statement merchant variations by character frequency signature
public static List<List<String>> groupAnagrams(String[] strs) {
    return Arrays.stream(strs)
            .collect(Collectors.groupingBy(GroupAnagrams::buildFrequencyKey, LinkedHashMap::new, Collectors.toList()))
            .values()
            .stream()
            .toList();
}
```

---

### 3. Java 21 Virtual Threads (Project Loom) for Concurrent Systems

```java
// Exercising 1,000 concurrent Virtual Threads against the HitCounter
try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
    for (int i = 0; i < 1_000; i++) {
        final int ts = 100 + (i % 50);
        executor.submit(() -> hitCounter.hit(ts));
    }
}
```
- **Unpinned Carrier Threads:** Locks use `ReentrantLock` and `ReentrantReadWriteLock`, allowing Loom to unpin the underlying OS carrier thread during contention.

---

## REST API Endpoints Reference

| Method | Path | Description | Sample Payload |
|---|---|---|---|
| `POST` | `/api/v1/twosum` | Solves Two Sum problem | `{"nums": [2, 7, 11, 15], "target": 9}` |
| `POST` | `/api/v1/stock/max-profit` | Computes max stock trading profit | `{"prices": [7, 1, 5, 3, 6, 4]}` |
| `POST` | `/api/v1/intervals/merge` | Merges overlapping interval tiers | `{"intervals": [{"start": 1, "end": 4}, {"start": 3, "end": 7}]}` |
| `POST` | `/api/v1/metrics/hit` | Records hit in rolling 300s counter | `?timestamp=100` (Optional) |
| `GET` | `/api/v1/metrics/hits` | Returns total hits in rolling window | `?timestamp=105` (Optional) |
| `GET` | `/api/v1/ratelimit/try-acquire` | Tests Token Bucket rate limiter | - |

---

## Running the Application & Tests

### Prerequisites
- **Java 21+** (JDK 21 or 25)
- **Maven 3.9+**

### Run the Test Suite
Executes unit tests, MockMvc integration tests, and 1,000 Virtual Thread stress tests:

```bash
cd java-leetcode
mvn test -o
```

Expected output:
```
[INFO] ---------------------< com.leetcode:java-leetcode >---------------------
[INFO] Building java-leetcode 1.0.0
[INFO] Results:
[INFO] Tests run: 27, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

### Launch the Server
```bash
cd java-leetcode
mvn spring-boot:run -o
```

Server starts on `http://localhost:8080`.
Actuator metrics available at `http://localhost:8080/actuator/health`.
