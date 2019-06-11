package ru.otus.hw.tf;

import lombok.SneakyThrows;
import ru.otus.hw.tf.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestEngine {

    private final ArrayList<Method> methodsBeforeEach = new ArrayList<>();
    private final ArrayList<Method> methodsAfterEach = new ArrayList<>();
    private final ArrayList<Method> methodsBeforeAll = new ArrayList<>();
    private final ArrayList<Method> methodsAfterAll = new ArrayList<>();
    private final ArrayList<Method> methodsTest = new ArrayList<>();
    private TestReport report;

    private final Class<?> testClass;

    public TestEngine(Class<?> testClass) {
        this.testClass = testClass;
        initMethods(this.testClass);
    }

    @SneakyThrows
    public void run(TestReport report) {
        this.report = report;
        report.init(testClass, methodsTest.size());
        methodRunner(methodsBeforeAll, null);
        for (Method method : methodsTest) {
            Object obj = testClass.getDeclaredConstructor().newInstance();
            try {
                methodRunner(methodsBeforeEach, obj);
                runTest(method, obj);
            } catch (InvocationTargetException e) {
                e.getTargetException().printStackTrace();
                break;
            } finally {
                methodRunner(methodsAfterEach, obj);
            }
        }
        methodRunner(methodsAfterAll, null);
        report.complete();
    }

    private void runTest(Method method, Object obj) {
        try {
            method.invoke(obj);
            report.incrementRunTest();
        } catch (InvocationTargetException e) {
            report.incrementError();
            e.getTargetException().printStackTrace();
        } catch (IllegalAccessException e) {
            report.incrementError();
            e.printStackTrace();
        }
    }

    private void methodRunner(List<Method> methods, Object obj) throws InvocationTargetException, IllegalAccessException {
        for (Method method : methods) {
            method.invoke(obj);
        }
    }

    private void initMethods(Class<?> testClass) {
        Method[] methods = testClass.getDeclaredMethods();
        for (Method method : methods) {
            distributionAnnotations(method);
        }
    }

    private void distributionAnnotations(Method method) {
        if (method.isAnnotationPresent(BeforeEach.class)) {
            methodsBeforeEach.add(method);
        }

        if (method.isAnnotationPresent(AfterEach.class)) {
            methodsAfterEach.add(method);
        }

        if (method.isAnnotationPresent(BeforeAll.class)) {
            methodsBeforeAll.add(method);
        }

        if (method.isAnnotationPresent(AfterAll.class)) {
            methodsAfterAll.add(method);
        }

        if (method.isAnnotationPresent(Test.class)) {
            methodsTest.add(method);
        }
    }
}
