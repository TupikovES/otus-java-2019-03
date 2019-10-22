package ru.otus.hw.webserver.service;

import ru.otus.hw.cache.core.CacheEngine;
import ru.otus.hw.hibernate.dao.JdbcTemplate;
import ru.otus.hw.webserver.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;

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

    public List<User> getAll() {
        EntityManager entityManager = jdbcTemplate.getEntityManager();
        TypedQuery<User> users = entityManager.createQuery("select us from User us", User.class);
        List<User> resultList = users.getResultList();
        return resultList.isEmpty() ? Collections.emptyList() : resultList;
    }

}
