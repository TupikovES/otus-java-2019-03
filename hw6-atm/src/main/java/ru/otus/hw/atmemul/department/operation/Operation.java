package ru.otus.hw.atmemul.department.operation;

import ru.otus.hw.atmemul.atm.ATM;

/**
 * Visitor
 */
public interface Operation {

    void perform(ATM atm);

}
