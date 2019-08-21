package ru.otus.hw.hibernate.dao;

import javax.transaction.Transactional;
import java.util.Optional;

public interface JdbcTemplate {

    @Transactional
    void create(Object entity);

    @Transactional
    <T> T update(T entity);

    @Transactional
    <T> void createOrUpdate(T entity);

    <T> Optional<T> findById(Object id, Class<T> clazz);
}
