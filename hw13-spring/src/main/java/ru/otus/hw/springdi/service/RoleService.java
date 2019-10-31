package ru.otus.hw.springdi.service;

import ru.otus.hw.hibernate.dao.JdbcTemplate;
import ru.otus.hw.springdi.domain.Role;

public class RoleService {

    private final JdbcTemplate jdbcTemplate;

    public RoleService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Role role) {
        return (Long) jdbcTemplate.createOrUpdate(role);
    }

}
