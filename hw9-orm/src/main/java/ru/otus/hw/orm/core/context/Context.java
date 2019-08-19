package ru.otus.hw.orm.core.context;

import ru.otus.hw.orm.core.Id;
import ru.otus.hw.orm.core.PersistentEntity;
import ru.otus.hw.orm.exception.IllegalEntityException;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private static Context ourInstance = new Context();
    public static Context getInstance() {
        return ourInstance;
    }

    private Map<String, PersistentEntity> entities = new HashMap<>();

    private Context() {
        throw new IllegalStateException("Is singleton pattern class, use getInstance() static method");
    }

    public void addEntity(Class<?> entityClass) throws IllegalEntityException {

    }

}
