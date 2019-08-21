package ru.otus.hw.hibernate.dao;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import java.util.Optional;

public class JdbcTemplateImpl implements JdbcTemplate {

    private EntityManager entityManager;

    public JdbcTemplateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Object entity) {
        entityManager.persist(entity);
    }

    @Override
    public <T> T update(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public <T> void createOrUpdate(T entity) {
        ((Session) entityManager).saveOrUpdate(entity);
    }

    @Override
    public <T> Optional<T> findById(Object id, Class<T> clazz) {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }
}
