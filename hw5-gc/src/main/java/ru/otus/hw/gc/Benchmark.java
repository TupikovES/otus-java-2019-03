package ru.otus.hw.gc;

import javax.management.NotificationEmitter;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.Calendar;
import java.util.List;

public class Benchmark {

    private List<GarbageCollectorMXBean> garbageCollectorMXBeans;
    private GarbageCreator garbageCreator;

    public Benchmark(GarbageCreator garbageCreator) {
        garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        this.garbageCreator = garbageCreator;
    }

    public void start() {
        initListener();
        garbageCreator.run();
        printStatistic();
    }

    private void initListener() {
        for (GarbageCollectorMXBean gcBean : garbageCollectorMXBeans) {
            System.out.println("GC name:" + gcBean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcBean;
            emitter.addNotificationListener(new GCListener(), null, null);
        }
    }

    private void printStatistic() {
        Calendar instance = Calendar.getInstance();
        for (GarbageCollectorMXBean gcBean : garbageCollectorMXBeans) {
            System.out.println("============" + gcBean.getName() + "============");
            System.out.println("Count: " + gcBean.getCollectionCount());
            instance.setTimeInMillis(gcBean.getCollectionTime());
            String time = String.valueOf(instance.get(Calendar.MILLISECOND) / 1000d);
            System.out.println("Time: " + time + " s");
            System.out.println("================================================");
        }
    }
}
