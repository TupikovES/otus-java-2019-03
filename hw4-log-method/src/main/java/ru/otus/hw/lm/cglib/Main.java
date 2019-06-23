package ru.otus.hw.lm.cglib;

import ru.otus.hw.lm.CalculatorImpl;

public class Main {

    public static void main(String[] args) {
        CalculatorImpl calculator = ProxyCreator.createCalculator();
        System.out.println(calculator.add(2, 2)); //с аннотацией @Log
        System.out.println(calculator.sub(111, 234));
    }

}
