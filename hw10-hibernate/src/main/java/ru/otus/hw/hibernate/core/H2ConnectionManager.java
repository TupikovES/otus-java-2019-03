package ru.otus.hw.hibernate.core;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class H2ConnectionManager implements ConnectionManager {

    private EntityManager entityManager;

    public H2ConnectionManager() {
        EntityManagerFactory commonUnit = Persistence.createEntityManagerFactory("CommonUnit");
        entityManager = commonUnit.createEntityManager();
    }

    @Override
    public EntityManager getEntityManager() {
        if (entityManager.isOpen()) {
            return entityManager;
        }
        throw new IllegalStateException("entity manager closed");
    }
}
