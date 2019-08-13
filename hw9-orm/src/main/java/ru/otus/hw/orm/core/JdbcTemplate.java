package ru.otus.hw.orm.core;

import java.util.Optional;

public interface JdbcTemplate<T, ID> {
    void create(T entity);
    void update(T entity);
    void createOrUpdate(T entity);
    Optional<T> load(ID id, Class<T> clazz);
}
