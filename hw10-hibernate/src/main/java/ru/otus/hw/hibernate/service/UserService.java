package ru.otus.hw.hibernate.service;

import ru.otus.hw.cache.core.CacheEngine;
import ru.otus.hw.hibernate.dao.JdbcTemplate;
import ru.otus.hw.hibernate.domain.User;

public class UserService {

    private final JdbcTemplate jdbcTemplate;
    private final CacheEngine<Long, User> cache;

    public UserService(JdbcTemplate jdbcTemplate, CacheEngine<Long, User> cache) {
        this.jdbcTemplate = jdbcTemplate;
        this.cache = cache;
    }

    public User getById(Long id) {
        return cache.get(id, key -> jdbcTemplate.findById(id, User.class).orElse(null));
    }

    public Long save(User user) {
        Long id = (Long) jdbcTemplate.createOrUpdate(user);
        if (id != null) {
            cache.put(id, user);
        }
        return id;
    }
}
