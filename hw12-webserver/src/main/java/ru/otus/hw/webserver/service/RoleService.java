package ru.otus.hw.webserver.service;

import ru.otus.hw.hibernate.dao.JdbcTemplate;
import ru.otus.hw.webserver.domain.Role;

public class RoleService {

    private final JdbcTemplate jdbcTemplate;

    public RoleService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Role role) {
        return (Long) jdbcTemplate.createOrUpdate(role);
    }

}
