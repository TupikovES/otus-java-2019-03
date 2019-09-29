package ru.otus.hw.cache.core;

import lombok.Getter;

@SuppressWarnings("WeakerAccess")
@Getter
public class CacheElement<K, V> {

    private final K key;
    private final V value;
    private final long creationTime;
    private long lastAccessTime;


    public CacheElement(K key, V value) {
        this.key = key;
        this.value = value;
        this.creationTime = getCurrentTime();
        this.lastAccessTime = getCurrentTime();
    }

    protected long getCurrentTime() {
        return System.currentTimeMillis();
    }

    public void setAccessed() {
        lastAccessTime = getCurrentTime();
    }

}
