package ru.otus.hw.atmemul;

import ru.otus.hw.atmemul.atm.ATM;
import ru.otus.hw.atmemul.atm.Par;
import ru.otus.hw.atmemul.atm.ParEntity;
import ru.otus.hw.atmemul.atm.SimpleATM;
import ru.otus.hw.atmemul.department.Department;
import ru.otus.hw.atmemul.department.SimpleDepartment;

import java.util.EnumMap;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        EnumMap<Par, Integer> parCount = new EnumMap<>(Par.class);
        parCount.put(Par.RUR_50, 100);
        parCount.put(Par.RUR_100, 100);
        parCount.put(Par.RUR_500, 50);
        parCount.put(Par.RUR_1000, 10);
        parCount.put(Par.RUR_5000, 5);

        ATM simpleATM_1 = new SimpleATM(parCount);
        Department department = new SimpleDepartment();
        department.addAtm(simpleATM_1);

        department.printAllReport();

        ParEntity par1 = new ParEntity(Par.RUR_10, 10);
        ParEntity par2 = new ParEntity(Par.RUR_50, 10);
        ParEntity par3 = new ParEntity(Par.RUR_1000, 10);

        simpleATM_1.deposit(par2, par3);
        simpleATM_1.printReport();

        List<ParEntity> withdraw = simpleATM_1.withdraw(31650);
        System.out.println(withdraw);

        department.recoverStateAllATM();
        department.printAllReport();



    }

}
