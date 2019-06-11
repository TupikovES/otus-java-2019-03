package ru.otus.hw.tf;

import lombok.SneakyThrows;
import ru.otus.hw.tf.annotation.BeforeEach;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;

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
