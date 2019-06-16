package ru.otus.hw.lm;

import ru.otus.hw.lm.annotation.Log;

public class CalculatorImpl implements Calculator {

    @Log
    @Override
    public int add(int a, int b) {
        return a + b;
    }

    @Override
    public int sub(int a, int b) {
        return a - b;
    }

}
