package ru.otus.hw.hibernate.dao;

import ru.otus.hw.hibernate.domain.AbstractEntity;

import java.io.Serializable;
import java.util.Optional;

public interface JdbcTemplate {

    <T extends AbstractEntity> Serializable create(T entity);

    <T extends AbstractEntity> Serializable createOrUpdate(T entity);

    <T> T update(T entity);

    <T> Optional<T> findById(Object id, Class<T> clazz);
}
