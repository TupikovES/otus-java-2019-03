package ru.otus.hw.atmemul.department;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw.atmemul.atm.ATM;
import ru.otus.hw.atmemul.atm.Par;
import ru.otus.hw.atmemul.atm.SimpleATM;

import java.util.EnumMap;

import static org.junit.jupiter.api.Assertions.*;

class SimpleDepartmentTest {

    private static final EnumMap<Par, Integer> initialParCount_1 = new EnumMap<>(Par.class);
    private static final EnumMap<Par, Integer> initialParCount_2 = new EnumMap<>(Par.class);

    private Department department;
    private ATM atm_1;
    private ATM atm_2;

    @BeforeAll
    static void setUp() {
        initialParCount_1.put(Par.RUR_50, 100);
        initialParCount_1.put(Par.RUR_100, 100);
        initialParCount_1.put(Par.RUR_500, 50);
        initialParCount_1.put(Par.RUR_1000, 10);
        initialParCount_1.put(Par.RUR_5000, 5);

        initialParCount_2.put(Par.RUR_10, 1000);
        initialParCount_2.put(Par.RUR_50, 10);
        initialParCount_2.put(Par.RUR_100, 100);
        initialParCount_2.put(Par.RUR_500, 50);
        initialParCount_2.put(Par.RUR_1000, 100);
    }

    @BeforeEach
    void beforeTest() {
        department = new SimpleDepartment();
        atm_1 = new SimpleATM(initialParCount_1);
        atm_2 = new SimpleATM(initialParCount_2);
    }

    @Test
    void addAtm() {
        department.addAtm(atm_1);
        department.addAtm(atm_2);
        assertEquals(((SimpleDepartment) department).getAtmList().size(), 2);
    }

    @Test
    void recoverStateAllATM() {
        department.addAtm(atm_1);
        department.addAtm(atm_2);
        int beforeTotal_1 = atm_1.total();
        int beforeTotal_2 = atm_2.total();
        atm_1.withdraw(5000);
        atm_2.withdraw(1000);
        assertEquals(atm_1.total(), beforeTotal_1 - 5000);
        assertEquals(atm_2.total(), beforeTotal_2 - 1000);
        department.recoverStateAllATM();
        assertEquals(atm_1.total(), beforeTotal_1);
        assertEquals(atm_2.total(), beforeTotal_2);
    }

    @Test
    void totalSum() {
    }
}