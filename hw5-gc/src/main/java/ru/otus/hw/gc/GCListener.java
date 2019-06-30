package ru.otus.hw.gc;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;

public class GCListener implements NotificationListener {
    @Override
    public void handleNotification(Notification notification, Object handback) {
        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
            GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
            String gcName = info.getGcName();
            String gcAction = info.getGcAction();
            String gcCause  = info.getGcCause();

            long startTime = info.getGcInfo().getStartTime();
            long duration = info.getGcInfo().getDuration();

            System.out.println("start:" + startTime + " Name:" + gcName + ", action:" + gcAction + ", gcCause:" + gcCause + "(" + duration + " ms)");
        }
    }
}
