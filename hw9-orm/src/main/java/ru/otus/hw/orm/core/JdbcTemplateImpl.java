package ru.otus.hw.orm.core;

import ru.otus.hw.orm.exception.IllegalEntityException;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class JdbcTemplateImpl implements JdbcTemplate {

    private static String insert = "insert into %s (%s) values (%s)";
    private static String update = "update %s set %s where %s=%s";
    private static String selectById = "select * from %s where %s=?";

    private DbExecutor executor;

    public JdbcTemplateImpl(DbExecutor executor) {
        this.executor = executor;
    }

    @Override
    public <T> void create(T entity) {
        Class<?> clazz = entity.getClass();
        try {
            Field[] fields = clazz.getDeclaredFields();
            Field id = getIdField(fields);
            String tableName = clazz.getSimpleName();
            StringBuilder columns = new StringBuilder();
            StringBuilder values = new StringBuilder();
            List<Object> param = new ArrayList<>();
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                createColumnsString(fields, columns, values, i);
                createParams(entity, fields[i], id, param);
                fields[i].setAccessible(false);
            }

            String sql = String.format(insert, tableName, columns.toString(), values.toString());
            executor.insert(sql, param);

        } catch (IllegalEntityException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }
    }

    private <T> void createParams(T entity, Field field, Field id, List<Object> values) throws IllegalAccessException {
        if (field.equals(id)) {
            values.add(null);
        } else {
            values.add(field.get(entity));
        }
    }

    private void createColumnsString(Field[] fields, StringBuilder columns, StringBuilder val, int i) {
        if (i < fields.length - 1) {
            columns.append(fields[i].getName()).append(", ");
            val.append("?, ");
        } else {
            columns.append(fields[i].getName());
            val.append("?");
        }
    }

    private Field getIdField(Field[] fields) throws IllegalEntityException {
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }
        throw new IllegalEntityException("Id field not found");
    }

    @Override
    public <T> void update(T entity) {

    }

    @Override
    public <T> void createOrUpdate(T entity) {

    }

    @Override
    public <T, ID> Optional<T> findById(ID id, Class<T> clazz) {
        try {
            String tableName = clazz.getSimpleName();
            Field[] fields = clazz.getDeclaredFields();
            String idColumn = getIdField(fields)
                    .getName();
            T instance = clazz.getDeclaredConstructor().newInstance();
            String sql = String.format(selectById, tableName, idColumn);
            List<T> select = executor.select(sql, Collections.singletonList(id), rs -> {
                for (Field field : fields) {
                    try {
                        field.setAccessible(true);
                        field.set(instance, rs.getObject(field.getName()));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
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
