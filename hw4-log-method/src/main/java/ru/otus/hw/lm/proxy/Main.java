package ru.otus.hw.lm.proxy;

import ru.otus.hw.lm.Calculator;

public class Main {

    public static void main(String[] args) {
        Calculator calculator = ProxyCreator.createCalculator();
        System.out.println(calculator.add(2, 2)); //с аннотацией @Log
        System.out.println(calculator.sub(5, 3));
    }

}
