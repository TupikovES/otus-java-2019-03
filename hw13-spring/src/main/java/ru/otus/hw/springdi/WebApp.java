package ru.otus.hw.springdi;

import lombok.extern.slf4j.Slf4j;
import ru.otus.hw.cache.core.CacheEngine;
import ru.otus.hw.cache.core.CacheEngineImpl;
import ru.otus.hw.hibernate.core.ConnectionManager;
import ru.otus.hw.hibernate.core.H2ConnectionManager;
import ru.otus.hw.hibernate.dao.JdbcTemplate;
import ru.otus.hw.hibernate.dao.JdbcTemplateImpl;
import ru.otus.hw.springdi.domain.Role;
import ru.otus.hw.springdi.domain.User;
import ru.otus.hw.springdi.service.RoleService;
import ru.otus.hw.springdi.service.UserService;

import javax.persistence.EntityManager;
import java.util.Collections;

@Slf4j
public class WebApp {

    public static void main(String[] args) {

        ConnectionManager connectionManager = new H2ConnectionManager();
        EntityManager entityManager = connectionManager.getEntityManager();
        JdbcTemplate template = new JdbcTemplateImpl(entityManager);

        UserService userService = createUserService(template);
        RoleService roleService = new RoleService(template);

        Role roleAdmin = new Role(null, "ADMIN");
        Role roleUser = new Role(null, "USER");
        roleService.save(roleAdmin);
        roleService.save(roleUser);

        initUsers(userService, roleUser);

        User user = new User(null, "admin", "qwerty", "Administrator", 21, Collections.singleton(roleAdmin));

        userService.save(user);

    }



    private static UserService createUserService(JdbcTemplate template) {
        CacheEngine<Long, User> cache = new CacheEngineImpl<>(2, 5_000, 10_000, false);
        return new UserService(template, cache);
    }

    private static void initUsers(UserService userService, Role role) {
        userService.save(new User(null, "user_1", "123", "username_1", 20, Collections.singleton(role)));
        userService.save(new User(null, "user_2", "123", "username_2", 22, Collections.singleton(role)));
        userService.save(new User(null, "user_3", "123", "username_3", 27, Collections.singleton(role)));
    }

}
