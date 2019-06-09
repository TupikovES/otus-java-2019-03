package ru.otus.hw.tf;

import lombok.SneakyThrows;
import ru.otus.hw.tf.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestEngine {

    private final ArrayList<Method> methodsBeforeEach = new ArrayList<>();
    private final ArrayList<Method> methodsAfterEach = new ArrayList<>();
    private final ArrayList<Method> methodsBeforeAll = new ArrayList<>();
    private final ArrayList<Method> methodsAfterAll = new ArrayList<>();
    private final ArrayList<Method> methodsTest = new ArrayList<>();

    private final Class<?> testClass;

    public TestEngine(Class<?> testClass) {
        this.testClass = testClass;
        initMethods(this.testClass);
    }

    @SneakyThrows
    public void run() {
        runBeforeAll();
        for (Method method : methodsTest) {
            Object obj = testClass.getDeclaredConstructor().newInstance();
            runBeforeEach(obj);
            try {
                method.invoke(obj);
            } catch (InvocationTargetException e) {
                Throwable throwable = e.getTargetException();
                System.out.println("Test '" + method.getName() +
                        "' with exception: " + throwable.getClass().getName() +
                        ", msg: " + throwable.getMessage());
            }
            runAfterEach(obj);
        }
        runAfterAll();
    }

    private void runBeforeEach(Object obj) throws InvocationTargetException, IllegalAccessException {
        for (Method beforeEach : methodsBeforeEach) {
            beforeEach.invoke(obj);
        }
    }

    private void runAfterEach(Object obj) throws InvocationTargetException, IllegalAccessException {
        for (Method afterEach : methodsAfterEach) {
            afterEach.invoke(obj);
        }
    }

    private void runBeforeAll() throws InvocationTargetException, IllegalAccessException {
        for (Method beforeAll : methodsBeforeAll) {
            beforeAll.invoke(null);
        }
    }

    private void runAfterAll() throws InvocationTargetException, IllegalAccessException {
        for (Method afterAll : methodsAfterAll) {
            afterAll.invoke(null);
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
