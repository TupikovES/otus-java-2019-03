package ru.otus.hw.atmemul.department.operation;

import ru.otus.hw.atmemul.atm.ATM;

/**
 * Операция вывода отчета {@link ATM}
 */
public class PrintReportOperation implements Operation {

    @Override
    public void perform(ATM atm) {
        System.out.println("---");
        atm.printReport();
        System.out.println("---");
    }
}
