package com.leetcode.p06_randomizedset;

import java.util.*;

/**
 * LeetCode #380: Insert Delete GetRandom O(1).
 * <p>
 * <b>FinTech Application:</b> Fair algorithmic liquidity pool sampling, randomized fraud inspection audits,
 * uniform randomized routing of order flows across multiple matching gateways (Robinhood).
 * <p>
 * <b>Data Structure:</b> {@link ArrayList} + {@link HashMap} (value -> index).<br>
 * Removal achieves strict O(1) by swapping the target element with the last element.
 */
public class RandomizedSet<T> {

    private final List<T> list;
    private final Map<T, Integer> indexMap;
    private final Random random;

    public RandomizedSet() {
        this(new Random());
    }

    public RandomizedSet(Random random) {
        this.list = new ArrayList<>();
        this.indexMap = new HashMap<>();
        this.random = random;
    }

    /**
     * Inserts a value to the set. Returns true if the set did not already contain the specified element.
     */
    public boolean insert(T val) {
        if (indexMap.containsKey(val)) {
            return false;
        }
        indexMap.put(val, list.size());
        list.add(val);
        return true;
    }

    /**
     * Removes a value from the set with strict O(1) time complexity.
     * Swaps with the tail element to avoid O(N) array shifts.
     */
    public boolean remove(T val) {
        Integer index = indexMap.get(val);
        if (index == null) {
            return false;
        }

        int lastIndex = list.size() - 1;
        T lastVal = list.get(lastIndex);

        // Move last element to the slot of the element being removed
        list.set(index, lastVal);
        indexMap.put(lastVal, index);

        // Remove last element in O(1)
        list.remove(lastIndex);
        indexMap.remove(val);

        return true;
    }

    /**
     * Get a random element from the set with uniform probability.
     */
    public T getRandom() {
        if (list.isEmpty()) {
            throw new NoSuchElementException("RandomizedSet is empty");
        }
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }

    public int size() {
        return list.size();
    }
}
