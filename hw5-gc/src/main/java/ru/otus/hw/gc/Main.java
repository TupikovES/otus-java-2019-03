package ru.otus.hw.gc;

/*
-Xms512m
-Xmx512m
-Xlog:gc=debug:file=./logs/gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/dump
-XX:+UseG1GC
 */
public class Main {

    public static void main(String[] args) {
        OutOfMemoryCycle outOfMemoryCycle = new OutOfMemoryCycle(100000, 1000);
        Benchmark benchmark = new Benchmark(outOfMemoryCycle);
        benchmark.start();
    }

}
