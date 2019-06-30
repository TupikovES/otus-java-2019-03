package ru.otus.hw.atmemul.atm;

/**
 * Номиналы купюр
 */
public enum Par {

    RUR_10(10, 1),
    RUR_50(50, 2),
    RUR_100(100, 3),
    RUR_500(500, 4),
    RUR_1000(1000, 5),
    RUR_5000(5000, 6);

    private int value;
    private int priority;

    Par(int value, int priority) {
        this.value = value;
        this.priority = priority;
    }

    public int getValue() {
        return value;
    }

    public int getPriority() {
        return priority;
    }
}
