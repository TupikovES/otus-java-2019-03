package ru.otus.hw.cache.core;

import java.util.function.Function;

public interface CacheEngine<K, V> {

    void put(K key, V value);

    V get(K key);

    V get(K key, Function<K, V> function);

    int getHitCount();

    int getMissCount();

    void dispose();
}
