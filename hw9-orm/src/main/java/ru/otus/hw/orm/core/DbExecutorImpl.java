package ru.otus.hw.orm.core;

import java.sql.*;
import java.util.*;
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
                fillParamsOfPreparedStatement(param, ps);
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
    public void update(String sql, List<Object> param) throws SQLException {
        try (Connection connection = connectionManager.getConnection()) {
            Savepoint preInsertSavepoint = connection.setSavepoint();
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                fillParamsOfPreparedStatement(param, ps);
                ps.executeUpdate();
                connection.commit();
            } catch (Exception e) {
                System.out.println("update error");
                e.printStackTrace();
                connection.rollback(preInsertSavepoint);
            }
        }
    }

    @Override
    public <T> List<T> select(String sql, List<Object> param, RowMapper<T> rowMapper) throws SQLException {
        try (Connection connection = connectionManager.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                fillParamsOfPreparedStatement(param, ps);
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

    @Override
    public <T> boolean isExist(String sql, List<Object> param, RowMapper<T> rowMapper) throws SQLException {
        try (Connection connection = connectionManager.getConnection()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                fillParamsOfPreparedStatement(param, ps);
                ps.execute();
                try (ResultSet rs = ps.getResultSet()) {
                    return Objects.nonNull(rowMapper.rowMap(rs));
                }
            }
        }
    }

    private void fillParamsOfPreparedStatement(List<Object> param, PreparedStatement ps) throws SQLException {
        for (int i = 0; i < param.size(); i++) {
            ps.setObject(i + 1, param.get(i));
        }
    }
}
