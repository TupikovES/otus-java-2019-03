package ru.otus.hw.orm.core.context;

import com.google.common.base.Joiner;
import lombok.Getter;
import ru.otus.hw.orm.core.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PersistentEntity {

    private static String SQL_INSERT_QUERY = "insert into %s (%s) values (%s)";
    private static String SQL_UPDATE_QUERY = "update %s set %s where %s=?";
    private static String SQL_SELECT_BY_ID_QUERY = "select * from %s where %s=?";
    private static String SQL_IF_EXIST = "select %s from %s where %s=?";

    private Class<?> entityClass;
    private String entityName;
    private Field idField;
    private String idColumnName;
    private Class<?> idColumnType;
    private List<String> columnsWithoutId = new ArrayList<>();
    private Field[] fieldsWithoutId;

    private String sqlInsertQuery;
    private String sqlUpdateQuery;
    private String sqlSelectByIdQuery;
    private String sqlIfExist;

    private List<String> columns = new ArrayList<>();

    public PersistentEntity(Class<?> entityClass) {
        this.entityClass = entityClass;
        entityName = entityClass.getSimpleName();
        parseClass(entityClass);
        createSqlInsertQuery();
        createSqlUpdateQuery();
        createSqlSelectByIdQuery();
        createSqlIfExist();
    }

    public List<Object> getValuesWithoutId(Object entity) throws IllegalAccessException {
        List<Object> values = new ArrayList<>();
        for (Field field : fieldsWithoutId) {
            field.setAccessible(true);
            values.add(field.get(entity));
            field.setAccessible(false);
        }
        return values;
    }

    public List<Object> getValues(Object entity) throws IllegalAccessException {
        List<Object> values = getValuesWithoutId(entity);
        idField.setAccessible(true);
        values.add(idField.get(entity));
        idField.setAccessible(false);
        return values;
    }

    public Object getIdValue(Object entity) throws IllegalAccessException {
        idField.setAccessible(true);
        Object value = idField.get(entity);
        idField.setAccessible(false);
        return value;
    }

    private void parseClass(Class<?> entityClass) {
        Field[] fields = entityClass.getDeclaredFields();
        fieldsWithoutId = new Field[fields.length - 1];
        List<Field> fieldList = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                idField = field;
                idColumnName = field.getName();
                idColumnType = field.getType();
            } else {
                fieldList.add(field);
                columnsWithoutId.add(field.getName());
            }
        }
        fieldList.toArray(fieldsWithoutId);
    }

    private void createSqlInsertQuery() {
        String columns = Joiner.on(",").join(columnsWithoutId);
        String preparedValues = Joiner.on(",").join(
                columnsWithoutId.stream()
                .map(s -> "?")
                .collect(Collectors.toList())
        );
        sqlInsertQuery = String.format(SQL_INSERT_QUERY, entityName, columns, preparedValues);
    }

    private void createSqlUpdateQuery() {
        String preparedValues = Joiner.on(",").join(
                columnsWithoutId.stream()
                        .map(s -> s + "=?")
                        .collect(Collectors.toList())
        );
        sqlUpdateQuery = String.format(SQL_UPDATE_QUERY, entityName, preparedValues, idColumnName);
    }

    private void createSqlSelectByIdQuery() {
        sqlSelectByIdQuery = String.format(SQL_SELECT_BY_ID_QUERY, entityName, idColumnName);
    }

    private void createSqlIfExist() {
        sqlIfExist = String.format(SQL_IF_EXIST, idColumnName, entityName, idColumnName);
    }
}
