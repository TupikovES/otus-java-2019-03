package ru.otus.hw.hibernate.core;

import javax.persistence.EntityManager;

public interface ConnectionManager {
    EntityManager getEntityManager();
}
