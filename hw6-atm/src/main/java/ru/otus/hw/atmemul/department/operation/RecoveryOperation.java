package ru.otus.hw.atmemul.department.operation;

import ru.otus.hw.atmemul.atm.ATM;

/**
 * Операция восстановления состояния {@link ATM}
 */
public class RecoveryOperation implements Operation {
    @Override
    public void perform(ATM atm) {
        System.out.println("Start recovery atm: " + atm.getAtmUuid());
        atm.recovery();
        System.out.println("Atm (" + atm.getAtmUuid() + ") is recovered");
    }
}
