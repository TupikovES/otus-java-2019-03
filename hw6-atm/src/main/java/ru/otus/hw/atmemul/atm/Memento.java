package ru.otus.hw.atmemul.atm;

import java.util.EnumMap;

public class Memento {

    private EnumMap<Par, Integer> parCount;

    public void save(EnumMap<Par, Integer> parCount) {
        this.parCount = parCount;
    }

    public EnumMap<Par, Integer> getState() {
        return parCount;
    }

}
