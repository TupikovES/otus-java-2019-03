package ru.otus.hw.tf.core;

import lombok.SneakyThrows;

public class TestClassRunner {

    @SneakyThrows
    public static void run(Class<?>... clazz) {
        TestReport report = new TestReport();
        for (Class<?> testClass : clazz) {
            new TestEngine(testClass).run(report);
        }
        report.printAllResult();
    }


}
