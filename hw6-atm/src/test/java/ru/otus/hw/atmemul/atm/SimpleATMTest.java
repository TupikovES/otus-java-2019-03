package ru.otus.hw.atmemul.atm;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SimpleATMTest {

    private static final EnumMap<Par, Integer> initialParCount = new EnumMap<>(Par.class);

    private ATM atm;

    @BeforeAll
    static void setUp() {
        initialParCount.put(Par.RUR_50, 100);
        initialParCount.put(Par.RUR_100, 100);
        initialParCount.put(Par.RUR_500, 50);
        initialParCount.put(Par.RUR_1000, 10);
        initialParCount.put(Par.RUR_5000, 5);
    }

    @BeforeEach
    void beforeTest() {
        atm = new SimpleATM(initialParCount);
    }

    @Test
    void depositPositiveTest() {
        ParEntity depositUnit = new ParEntity(Par.RUR_1000, 10);
        int beforeTotal = atm.total();
        atm.deposit(depositUnit);
        assertEquals(atm.total(), depositUnit.total() + beforeTotal);
    }

    @Test
    void depositNegativeTest() {
        ParEntity depositUnit = new ParEntity(Par.RUR_10, 1000);
        int beforeTotal = atm.total();
        atm.deposit(depositUnit);
        assertEquals(atm.total(), beforeTotal);
    }

    @Test
    void withdrawPositiveTest() {
        int beforeTotal = atm.total();
        List<ParEntity> withdraw = atm.withdraw(6000);
        assertEquals(atm.total() , beforeTotal - 6000);
        assertEquals(withdraw.size(), 2);
    }

    @Test
    void withdrawNegativeTest() {
        int beforeTotal = atm.total();
        List<ParEntity> withdraw = atm.withdraw(6510);
        assertEquals(atm.total() , beforeTotal);
        assertTrue(withdraw.isEmpty());
    }

    @Test
    void returnInitialState() {
        int beforeTotal = atm.total();
        atm.withdraw(5000);
        assertEquals(atm.total(), beforeTotal - 5000);
        atm.recovery();
        assertEquals(atm.total(), beforeTotal);
    }

}