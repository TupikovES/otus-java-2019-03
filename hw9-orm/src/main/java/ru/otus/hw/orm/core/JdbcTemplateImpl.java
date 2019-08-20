package ru.otus.hw.orm.core;

import ru.otus.hw.orm.core.context.Context;
import ru.otus.hw.orm.core.context.PersistentEntity;
import ru.otus.hw.orm.exception.IllegalEntityException;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.*;

public class JdbcTemplateImpl implements JdbcTemplate {

    private static String exist = "select %s from %s where %s=?";

    private DbExecutor executor;
    private Context context;

    public JdbcTemplateImpl(DbExecutor executor) {
        this.executor = executor;
        this.context = Context.getInstance();
    }

    @Override
    public <T> void create(T entity) {
        try {
            PersistentEntity pe = context.getPersistentEntity(entity);
            String sqlInsertQuery = pe.getSqlInsertQuery();
            executor.insert(sqlInsertQuery, pe.getValuesWithoutId(entity));
        } catch (IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> void update(T entity) {
        try {
            PersistentEntity pe = context.getPersistentEntity(entity);
            executor.update(pe.getSqlUpdateQuery(), pe.getValues(entity));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean checkExist(Class<?> clazz, Object id) throws SQLException {
        PersistentEntity pe = context.getPersistentEntity(clazz);
        String sqlIfExist = pe.getSqlIfExist();
        return executor.isExist(sqlIfExist, Collections.singletonList(id), rs -> rs.getObject(pe.getIdColumnName()));
    }

    @Override
    public <T> void createOrUpdate(T entity) {
        try {
            PersistentEntity pe = context.getPersistentEntity(entity);
            Object id = pe.getIdField().get(entity);
            if (Objects.isNull(id) || !checkExist(pe.getEntityClass(), id)) {
                create(entity);
            } else {
                update(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T, ID> Optional<T> findById(ID id, Class<T> clazz) {
        try {
            PersistentEntity pe = context.getPersistentEntity(clazz);
            T instance = clazz.getDeclaredConstructor().newInstance();
            List<T> select = executor.select(pe.getSqlSelectByIdQuery(), Collections.singletonList(id), rs -> {
                try {
                    for (Field field : pe.getFieldsWithoutId()) {

                        field.setAccessible(true);
                        field.set(instance, rs.getObject(field.getName()));
                        field.setAccessible(false);
                    }
                    pe.getIdField().setAccessible(true);
                    pe.getIdField().set(instance, rs.getObject(pe.getIdColumnName()));
                    pe.getIdField().setAccessible(false);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return instance;
            });
            if (select.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(select.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
