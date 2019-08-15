package ru.otus.hw.orm.core;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
public class H2ConnectionManager implements ConnectionManager {

    private static final String DB_URL = "jdbc:h2:mem:test";

    private Connection initConnection;

    public H2ConnectionManager() throws SQLException, ClassNotFoundException {
        log.info("info");
        Class.forName("org.h2.Driver");
        initConnection = createTable();
    }

    private Connection createTable() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_URL);
        connection.setAutoCommit(false);
        PreparedStatement ps = connection.prepareStatement(
                "create table Person (" +
                        "id identity not null primary key," +
                        "username varchar not null" +
                        ")"
        );
        ps.execute();
        return connection;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public void close() throws SQLException {
        initConnection.close();
    }
}
