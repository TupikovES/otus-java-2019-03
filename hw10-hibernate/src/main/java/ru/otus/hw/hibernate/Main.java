package ru.otus.hw.hibernate;

import lombok.extern.slf4j.Slf4j;
import ru.otus.hw.cache.core.CacheEngine;
import ru.otus.hw.cache.core.CacheEngineImpl;
import ru.otus.hw.hibernate.core.ConnectionManager;
import ru.otus.hw.hibernate.core.H2ConnectionManager;
import ru.otus.hw.hibernate.dao.JdbcTemplate;
import ru.otus.hw.hibernate.dao.JdbcTemplateImpl;
import ru.otus.hw.hibernate.domain.Address;
import ru.otus.hw.hibernate.domain.Phone;
import ru.otus.hw.hibernate.domain.User;
import ru.otus.hw.hibernate.service.UserService;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class Main {

    public static void main(String[] args) throws InterruptedException {

        log.info("Starting...");

        ConnectionManager connectionManager = new H2ConnectionManager();
        EntityManager entityManager = connectionManager.getEntityManager();
        JdbcTemplate template = new JdbcTemplateImpl(entityManager);
        CacheEngine<Long, User> cache = new CacheEngineImpl<>(2, 5_000, 10_000, false);

        UserService userService = new UserService(template, cache);

        Phone phone_1 = Phone.builder().number("111-111-111").build();
        Phone phone_2 = Phone.builder().number("222-222-222").build();

        List<Phone> phones = new ArrayList<>();
        phones.add(phone_1);
        phones.add(phone_2);

        Address street_1 = Address.builder().street("street_1").build();

        User user_1 = User.builder()
                .name("user_1")
                .age(1)
                .address(street_1)
                .phones(phones)
                .build();

        Long id = userService.save(user_1);
        User cacheUser = userService.getById(id);

        Thread.sleep(10_000);

        User dbUser = userService.getById(id);

    }
}
