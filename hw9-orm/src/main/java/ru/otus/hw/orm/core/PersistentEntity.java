package ru.otus.hw.orm.core;

import java.util.ArrayList;
import java.util.List;

public class PersistentEntity {

    private static String SQL_INSERT_QUERY = "insert into %s (%s) values (%s)";
    private static String SQL_UPDATE_QUERY = "update %s set %s where %s=?";
    private static String SQL_SELECT_BY_ID_QUERY = "select * from %s where %s=?";

    private String className;
    private String entityName;
    private String idColumnName;
    private Class<?> idColumnType;
    private String sqlQuery;

    private List<String> columns = new ArrayList<>();

    public PersistentEntity(Class<?> entityClass) {

    }

    public boolean hasIdColumn(Class<?> entityClass) {
        return entityClass.isAnnotationPresent(Id.class);
    }
}
