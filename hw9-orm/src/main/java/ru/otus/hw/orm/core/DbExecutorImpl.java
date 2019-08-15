package ru.otus.hw.orm.core;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class DbExecutorImpl implements DbExecutor {

    private final ConnectionManager connectionManager;

    public DbExecutorImpl(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Object insert(String sql, List<Object> param) throws SQLException {
        try (Connection connection = connectionManager.getConnection()) {
            Savepoint preInsertSavepoint = connection.setSavepoint();
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < param.size(); i++) {
                    ps.setObject(i + 1, param.get(i));
                }
                ps.executeUpdate();
                connection.commit();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs != null && rs.next()) {
                        return rs.getLong(1);
                    }
                }
            } catch (Exception e) {
                System.out.println("insert error");
                e.printStackTrace();
                connection.rollback(preInsertSavepoint);
            }
            return null;
        }
    }

    @Override
    public <T> List<T> select(String sql, List<Object> param, RowMapper<T> rowMapper) throws SQLException {
        try (Connection connection = connectionManager.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                for (int i = 0; i < param.size(); i++) {
                    ps.setObject(i + 1, param.get(i));
                }
                ps.execute();
                try (ResultSet rs = ps.getResultSet()) {
                    List<T> results = new ArrayList<>();
                    if (rs != null && rs.next()) {
                        results.add(rowMapper.rowMap(rs));
                    }
                    return results;
                }
            }
        }
    }


}
