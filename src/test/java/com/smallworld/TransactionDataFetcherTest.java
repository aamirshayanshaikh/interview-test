package com.smallworld;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
public class TransactionDataFetcherTest {

    @Mock
    TransactionDataFetcher dataFetcher;

    List<Transaction> mockedTransactions;

    @BeforeEach
    void init(){
        mockedTransactions = new ArrayList<>();
        dataFetcher = new TransactionDataFetcher();
        mockedTransactions.add(new Transaction(112L, 25.0, "Aamir shayan",
                23, "Kamran Ahmed", 22, 11123,
                true, "true"));

        mockedTransactions.add(new Transaction(113L, 100.0, "Uzair Ahmed",
                24, "Samiullah", 23, 11124,
                false, "false"));

        mockedTransactions.add(new Transaction(114L, 75.0, "Abdul Sami",
                25, "Ali", 25, 11125,
                true, "true"));

        mockedTransactions.add(new Transaction(114L, 75.0, "Aamir shayan",
                25, "Ali", 25, 11125,
                true, "true"));
    }


    @Test
    void testGetTotalTransaction_whenSum() {
        assertEquals(275.0, dataFetcher.getTotalTransactionAmount(mockedTransactions),
                "test success");
    }

    @Test
    void testGetTotalTransaction_whenSentBySamiullah_returnSum() {
        assertEquals(100, dataFetcher.getTotalTransactionAmountSentBy("Aamir shayan", mockedTransactions),
                "test success");
    }

    @Test
    void testGetMaxTransaction_whenAllTransactionsAvailable_returnMaxAmount() {
        assertEquals(100, dataFetcher.getMaxTransactionAmount(mockedTransactions),
                "test success");
    }

    @Test
    void testCount_whenUniqueClients_returnNumber() {
        assertEquals(3, dataFetcher.countUniqueClients(mockedTransactions),
                "test success");
    }

    @Test
    void testFindCompliance_whenIssueIsOpen_returnList() {
        assertNotNull(dataFetcher.hasOpenComplianceIssues("Uzair Ahmed", mockedTransactions));
        assertEquals(1, dataFetcher.hasOpenComplianceIssues("Uzair Ahmed", mockedTransactions).size(),
                "test success");
    }
}
