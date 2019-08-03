package ru.otus.hw.atmemul.department;

import ru.otus.hw.atmemul.atm.ATM;
import ru.otus.hw.atmemul.department.operation.Operation;
import ru.otus.hw.atmemul.department.operation.PrintReportOperation;
import ru.otus.hw.atmemul.department.operation.RecoveryOperation;

import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleDepartment implements Department {

    private Map<String, ATM> atmList;

    public SimpleDepartment() {
        atmList = new LinkedHashMap<>();
    }

    @Override
    public void addAtm(ATM atm) {
        atmList.put(atm.getAtmUuid(), atm);
    }

    @Override
    public void printAllReport() {
        System.out.println("Print all atm report:");
        Operation printReport = new PrintReportOperation();
        atmList.forEach((s, atm) -> atm.perform(printReport));
        //atmList.forEach((s, atm) -> atm.printReport());
        System.out.println("----- end all report -----");
    }

    @Override
    public void recoverStateAllATM() {
        Operation recovery = new RecoveryOperation();
        atmList.forEach((s, atm) -> atm.perform(recovery));
    }

    @Override
    public long totalSum() {
        return  atmList.values()
                .stream()
                .mapToLong(ATM::total)
                .sum();
    }

}
