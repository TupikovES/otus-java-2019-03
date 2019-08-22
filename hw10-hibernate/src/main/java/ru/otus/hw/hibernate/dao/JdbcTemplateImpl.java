package ru.otus.hw.hibernate.dao;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Optional;

public class JdbcTemplateImpl implements JdbcTemplate {

    private EntityManager entityManager;

    public JdbcTemplateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void create(Object entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManager.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    @Override
    public <T> T update(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            T updateable = entityManager.merge(entity);
            transaction.commit();
            return updateable;
        } catch (Exception e) {
            transaction.rollback();
            return null;
        }
    }

    @Override
    public <T> void createOrUpdate(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            ((Session) entityManager).saveOrUpdate(entity);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    @Override
    public <T> Optional<T> findById(Object id, Class<T> clazz) {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }
}
