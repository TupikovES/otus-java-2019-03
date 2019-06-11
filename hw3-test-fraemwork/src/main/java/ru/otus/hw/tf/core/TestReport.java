package ru.otus.hw.tf.core;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TestReport {

    private int runTest;
    private int testSkipped;
    private int testError;
    private AllTestReport allTestReport;

    public TestReport() {
        this.allTestReport = new AllTestReport();
    }

    public void init(Class<?> clazz, int testSize) {
        System.out.println("Running " + clazz.getName());
        testSkipped = testSize;
    }

    public void complete() {
        printReport();
        allTestReport.setResult(runTest, testError, testSkipped);
        runTest = 0;
        testSkipped = 0;
        testError = 0;
    }

    public void incrementRunTest() {
        runTest++;
        testSkipped--;
    }

    public void incrementError() {
        testSkipped--;
        testError++;
    }

    private void printReport() {
        System.out.println("---TEST REPORT---");
        System.out.println(
                "run test: " + runTest +
                " errors: " + testError +
                " skipped: " + testSkipped);
        System.out.println();
    }

    public void printAllResult() {
        allTestReport.printAllReport();
    }

    @Getter
    private class AllTestReport {
        private int allRunTest;
        private int allTestSkipped;
        private int allTestError;

        void setResult(int run, int error, int skipped) {
            allRunTest += run;
            allTestError += error;
            allTestSkipped += skipped;
        }

        void printAllReport() {
            System.out.println("---ALL TEST REPORT---");
            System.out.println(
                    "run test: " + allRunTest +
                            " errors: " + allTestError +
                            " skipped: " + allTestSkipped);
        }

    }

}
