package ru.otus.hw.atmemul.department;

import ru.otus.hw.atmemul.atm.ATM;

public interface Department {

    /**
     * Прикрепить ATM к банкомату
     *
     * @param atm {@link ATM}
     */
    void addAtm(ATM atm);

    /**
     * Вывести состояние всех ATM
     */
    void printAllReport();

    /**
     * Восстановить состояние всех ATM
     */
    void recoverStateAllATM();

    /**
     * Посчитать сумму остатков cо всех {@link ATM}
     *
     * @return сумма всех остатков
     */
    long totalSum();

}
