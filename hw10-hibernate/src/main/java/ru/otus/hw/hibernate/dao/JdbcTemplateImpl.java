package ru.otus.hw.hibernate.dao;

import org.hibernate.Session;
import ru.otus.hw.hibernate.domain.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public class JdbcTemplateImpl implements JdbcTemplate {

    private EntityManager entityManager;

    public JdbcTemplateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public <T extends AbstractEntity> Serializable create(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            entityManager.persist(entity);
            transaction.commit();
            return entity.getId();
        } catch (Exception e) {
            transaction.rollback();
            return null;
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
    public <T extends AbstractEntity> Serializable createOrUpdate(T entity) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            ((Session) entityManager).saveOrUpdate(entity);
            transaction.commit();
            return entity.getId();
        } catch (Exception e) {
            transaction.rollback();
            return null;
        }
    }

    @Override
    public <T> Optional<T> findById(Object id, Class<T> clazz) {
        return Optional.ofNullable(entityManager.find(clazz, id));
    }
}
