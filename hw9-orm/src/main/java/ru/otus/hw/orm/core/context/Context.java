package ru.otus.hw.orm.core.context;

import ru.otus.hw.orm.core.Id;
import ru.otus.hw.orm.exception.IllegalEntityException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Context {
    private static Context ourInstance = new Context();
    public static Context getInstance() {
        return ourInstance;
    }

    private Map<String, PersistentEntity> entities = new HashMap<>();

    private Context() {}

    public void addEntity(Class<?> entityClass) throws IllegalEntityException {
        if (hasIdColumn(entityClass)) {
            PersistentEntity old = entities.put(entityClass.getSimpleName(), new PersistentEntity(entityClass));
            if (old != null) {
                throw new IllegalStateException("Дублирование классов: old=" + old.getEntityClass().getName() +
                        " new=" + entityClass.getName());
            }
        }
    }

    public PersistentEntity getPersistentEntity(Object entity) {
        return entities.get(entity.getClass().getSimpleName());
    }

    public PersistentEntity getPersistentEntity(Class<?> entity) {
        return entities.get(entity.getSimpleName());
    }

    private boolean hasIdColumn(Class<?> entityClass) {
        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return true;
            }
        }
        return false;
    }

}
