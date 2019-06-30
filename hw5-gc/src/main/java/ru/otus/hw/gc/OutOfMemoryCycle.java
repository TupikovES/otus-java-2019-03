package ru.otus.hw.gc;

import java.util.ArrayList;
import java.util.List;

public class OutOfMemoryCycle implements GarbageCreator {

    private int loopCounter;
    private List<String> list = new ArrayList<>();
    private int size;
    private List<Object[]> trashcan = new ArrayList<>();

    public OutOfMemoryCycle(int loopCounter, int size) {
        this.loopCounter = loopCounter;
        this.size = size;
    }

    public void run() {
        System.out.println("Run...");
        for (int idx = 0; idx < loopCounter; idx++) {
            int local = size;
            Object[] array = new Object[local];
            for (int i = 0; i < local; i++) {
                array[i] = new String(new char[0]);
            }
            sleep(10);
            trashcan.add(array);
            if ((idx + 1) % 10 == 0) {
                for (int i = 0; i < 5; i++) {
                    trashcan.remove(i);
                }
            }
        }
        System.out.println("END");
    }

    private void sleep(long sleepTime) {
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}
