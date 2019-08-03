package ru.otus.hw.atmemul.atm;

import ru.otus.hw.atmemul.department.operation.Operation;

import java.util.List;

public interface ATM {

    /**
     * Уникальный номер банкомата
     * @return uuid
     */
    String getAtmUuid();

    /**
     * Сумма остатка денежных средств ATM
     */
    int total();

    /**
     * Внести средства.
     * В банкомат вносятся купюры. Поэтому пользователь не сможет внести сумму несооьветствующую номиналу купюр.
     * Считаем что банкомат посчитал купюры и знает количество каждого номинала.
     *
     * @param parEntities количество купюр определенныи номиналом
     */
    void deposit(ParEntity ...parEntities);

    /**
     * Сумма для снятия
     * @param value сумма
     */
    List<ParEntity> withdraw(int value);

    /**
     * Вывести отчет о состояниии банкомата
     */
    void printReport();

    /**
     * Востановить состояние {@link ATM}
     */
    void recovery();

    /**
     * Выполнить операцию
     * @param operation {@link Operation}
     */
    void perform(Operation operation);
}
