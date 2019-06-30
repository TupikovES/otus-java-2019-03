package ru.otus.hw.atmemul;

import ru.otus.hw.atmemul.atm.ATM;
import ru.otus.hw.atmemul.atm.Par;
import ru.otus.hw.atmemul.atm.ParEntity;
import ru.otus.hw.atmemul.atm.SimpleATM;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        ATM simpleATM_1 = new SimpleATM();

        ParEntity par1 = new ParEntity(Par.RUR_10, 10);
        ParEntity par2 = new ParEntity(Par.RUR_50, 10);
        ParEntity par3 = new ParEntity(Par.RUR_1000, 10);

        simpleATM_1.printReport();

        simpleATM_1.deposit(par2, par3);
        simpleATM_1.printReport();

        List<ParEntity> withdraw = simpleATM_1.withdraw(31650);
        System.out.println(withdraw);
        simpleATM_1.printReport();
    }

}
