package ru.otus.hw.orm.core;

import java.sql.SQLException;
import java.util.List;

public interface DbExecutor {

    Object insert(String sql, List<Object> param) throws SQLException;
    <T> List<T> select(String sql, List<Object> param, RowMapper<T> rowMapper) throws SQLException;

}
