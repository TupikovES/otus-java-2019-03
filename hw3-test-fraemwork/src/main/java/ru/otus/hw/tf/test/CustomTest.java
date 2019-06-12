package ru.otus.hw.tf.test;

import ru.otus.hw.tf.annotation.*;

public class CustomTest {

    @BeforeAll
    public static void setUpClass() {
        System.out.println("run @BeforeAll");
    }

    @AfterAll
    public static void tearDownClass() {
        System.out.println("run @AfterAll");
    }

    @BeforeEach
    public void setUpTest() {
        System.out.println("run @BeforeEach");
    }

    @AfterEach
    public void tearDownMethod() {
        System.out.println("run @AfterEach");
    }

    @Test
    public void oneTest() {
        System.out.println("run oneTest test");
    }

    @Test
    public void exceptionTest() throws NullPointerException {
        System.out.println("run exception test");
        throw new IndexOutOfBoundsException("Test exception by exceptionTest()");
    }

    @Test
    public void twoTest() {
        System.out.println("run twoTest test");
    }
}
