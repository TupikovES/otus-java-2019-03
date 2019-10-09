package ru.otus.hw.webserver;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.security.SecurityHandler;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.SessionIdManager;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.session.DefaultSessionCache;
import org.eclipse.jetty.server.session.DefaultSessionIdManager;
import org.eclipse.jetty.server.session.NullSessionDataStore;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import ru.otus.hw.cache.core.CacheEngine;
import ru.otus.hw.cache.core.CacheEngineImpl;
import ru.otus.hw.hibernate.core.ConnectionManager;
import ru.otus.hw.hibernate.core.H2ConnectionManager;
import ru.otus.hw.hibernate.dao.JdbcTemplate;
import ru.otus.hw.hibernate.dao.JdbcTemplateImpl;
import ru.otus.hw.webserver.domain.Role;
import ru.otus.hw.webserver.domain.User;
import ru.otus.hw.webserver.configuration.ServerConfiguration;
import ru.otus.hw.webserver.service.RoleService;
import ru.otus.hw.webserver.service.UserService;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.Collections;

@Slf4j
public class WebApp {

    public static void main(String[] args) throws Exception {

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

        Server server = initialServer(userService);
        server.start();
        server.join();
    }

    private static Server initialServer(UserService userService) throws IOException {
        ServletContextHandler contextHandler = ServerConfiguration.createHandler(userService);
        SecurityHandler securityHandler = ServerConfiguration.createSecurityHandler(contextHandler);
        ResourceHandler resourceHandler = ServerConfiguration.createResourceHandler();
        Server server = new Server(ServerConfiguration.PORT);

        SessionHandler sessionHandler = new SessionHandler();
        DefaultSessionCache defaultSessionCache = new DefaultSessionCache(sessionHandler);
        defaultSessionCache.setSessionDataStore(new NullSessionDataStore());
        sessionHandler.setSessionCache(defaultSessionCache);

        contextHandler.setSessionHandler(sessionHandler);

        server.setHandler(new HandlerList(contextHandler));

        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[]{/*sessionHandler,*/ resourceHandler, securityHandler});

        server.setHandler(handlerList);

        return server;
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
