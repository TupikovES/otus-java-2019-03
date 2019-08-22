package ru.otus.hw.hibernate.dao;

import java.util.Optional;

public interface JdbcTemplate {

    void create(Object entity);

    <T> T update(T entity);

    <T> void createOrUpdate(T entity);

    <T> Optional<T> findById(Object id, Class<T> clazz);
}
