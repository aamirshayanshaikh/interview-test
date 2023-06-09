package com.smallworld;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TransactionDataFetcher {

    /**
     * Returns the sum of the amounts of all transactions
     */
    public double getTotalTransactionAmount(List<Transaction> transaction) {
        if (Objects.nonNull(transaction)) {
            return transaction.stream()
                            .mapToDouble((Transaction::getAmount)).sum();
        }
       return 0;
    }

    /**
     * Returns the sum of the amounts of all transactions sent by the specified client
     */
    public double getTotalTransactionAmountSentBy(String senderFullName, List<Transaction> transaction) {
        if (Objects.nonNull(transaction)) {
            return transaction.stream().filter(transaction1 ->
                            transaction1.getSenderFullName().equals(senderFullName))
                            .mapToDouble((Transaction::getAmount))
                            .sum();
        }
        return 0;
    }

    /**
     * Returns the highest transaction amount
     */
    public double getMaxTransactionAmount(List<Transaction> transaction) {
        if (Objects.nonNull(transaction)){
            return transaction.stream().
                            max(Comparator
                                    .comparing(Transaction::getAmount)).get().getAmount();

        }
       return 0;
    }

    /**
     * Counts the number of unique clients that sent or received a transaction
     */
    public long countUniqueClients(List<Transaction> transaction) {
        if (Objects.nonNull(transaction)) {
            return transaction.stream()
                    .filter(distinctByKey(Transaction::getSenderFullName))
                    .collect(Collectors.toList()).size();
        }
        return 0;
    }

    public  <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    /**
     * Returns whether a client (sender or beneficiary) has at least one transaction with a compliance
     * issue that has not been solved
     */
    public List<Transaction> hasOpenComplianceIssues(String clientFullName, List<Transaction> transaction) {
        if (Objects.nonNull(transaction)) {
            return transaction.stream()
                    .filter(tr -> !tr.getIssueSolved())
                    .filter(tr -> tr.getSenderFullName().equals(clientFullName))
                    .collect(Collectors.toList());

        }
      return Collections.emptyList();
    }

    /**
     * Returns all transactions indexed by beneficiary name
     */
    public  Map<String, List<Transaction>> getTransactionsByBeneficiaryName(List<Transaction> transaction) {
        Map<String, List<Transaction>> map = null;
        for (Transaction value : transaction) {
            if (map == null) {
                map = new HashMap<>();
            }
            String beneficiaryFullNameName = value.getBeneficiaryFullName();
            if (map.containsKey(value.getBeneficiaryFullName())) {
                List<Transaction> savedList = map.get(beneficiaryFullNameName);
                savedList.add(value);
                map.put(beneficiaryFullNameName, savedList);
            } else {
                List<Transaction> newList = new ArrayList<>();
                newList.add(value);
                map.put(beneficiaryFullNameName, newList);
            }
        }
        return map;
    }

    /**
     * Returns the identifiers of all open compliance issues
     */
    public List<Integer> getUnsolvedIssueIds(List<Transaction> transaction) {
        if (Objects.nonNull(transaction)) {
            return transaction.stream()
                    .filter(tr -> !tr.getIssueSolved())
                    .map((Transaction tr) -> tr.getIssueId())
                    .collect(Collectors.toList());

        }
        return Collections.emptyList();
    }

    /**
     * Returns a list of all solved issue messages
     */
    public List<String> getAllSolvedIssueMessages(List<Transaction> transaction) {
        if (Objects.nonNull(transaction)) {
            return transaction.stream()
                    .filter(tr -> tr.getIssueSolved())
                    .map((Transaction tr) -> tr.getIssueMessage())
                    .collect(Collectors.toList());

        }
        return Collections.emptyList();
    }

    /**
     * Returns the 3 transactions with highest amount sorted by amount descending
     */
    public List<Object> getTop3TransactionsByAmount() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns the sender with the most total sent amount
     */
    public Optional<Transaction> getTopSender(List<Transaction> transaction) {
        if (Objects.nonNull(transaction)){
            return transaction.stream().
                    max(Comparator
                            .comparing(Transaction::getAmount));

        };
        return Optional.of(new Transaction());
    }

}
