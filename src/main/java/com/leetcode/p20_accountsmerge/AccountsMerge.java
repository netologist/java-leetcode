package com.leetcode.p20_accountsmerge;

import java.util.*;

/**
 * LeetCode #721: Accounts Merge.
 * <p>
 * <b>FinTech Application:</b> KYC Single Customer View (SCV), Sybil attack detection,
 * consolidating multiple email / bank accounts belonging to the same beneficial owner (Plaid, Stripe).
 * <p>
 * <b>Algorithm:</b> Disjoint Set Union (DSU / Union-Find) with path compression and rank.<br>
 * <b>Time Complexity:</b> O(N * K log(N * K)) due to sorting emails.<br>
 * <b>Space Complexity:</b> O(N * K).
 */
public class AccountsMerge {

    public record Account(String name, List<String> emails) {
        public Account {
            emails = List.copyOf(emails);
        }
    }

    private static class UnionFind {
        private final Map<String, String> parent = new HashMap<>();

        String find(String x) {
            parent.putIfAbsent(x, x);
            if (!x.equals(parent.get(x))) {
                parent.put(x, find(parent.get(x))); // Path compression
            }
            return parent.get(x);
        }

        void union(String a, String b) {
            String rootA = find(a);
            String rootB = find(b);
            if (!rootA.equals(rootB)) {
                parent.put(rootA, rootB);
            }
        }
    }

    public static List<Account> accountsMerge(List<Account> accounts) {
        if (accounts == null || accounts.isEmpty()) {
            return List.of();
        }

        UnionFind uf = new UnionFind();
        Map<String, String> emailToName = new HashMap<>();

        // 1. Connect all emails within each account
        for (Account acc : accounts) {
            String name = acc.name();
            List<String> emails = acc.emails();
            if (emails.isEmpty()) continue;

            String firstEmail = emails.get(0);
            for (String email : emails) {
                emailToName.put(email, name);
                uf.union(firstEmail, email);
            }
        }

        // 2. Group emails by root component
        Map<String, Set<String>> rootToEmails = new HashMap<>();
        for (String email : emailToName.keySet()) {
            String root = uf.find(email);
            rootToEmails.computeIfAbsent(root, k -> new TreeSet<>()).add(email);
        }

        // 3. Assemble merged Account records
        List<Account> mergedAccounts = new ArrayList<>();
        for (var entry : rootToEmails.entrySet()) {
            String rootEmail = entry.getKey();
            String name = emailToName.get(rootEmail);
            List<String> sortedEmails = new ArrayList<>(entry.getValue());
            mergedAccounts.add(new Account(name, sortedEmails));
        }

        return List.copyOf(mergedAccounts);
    }
}
