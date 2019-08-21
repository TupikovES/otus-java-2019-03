package ru.otus.hw.orm.core;

import java.util.Optional;

public interface JdbcTemplate {
    <T> void create(T entity);
    <T> void update(T entity);
    <T> void createOrUpdate(T entity);
    <T, ID> Optional<T> findById(ID id, Class<T> clazz);
}
