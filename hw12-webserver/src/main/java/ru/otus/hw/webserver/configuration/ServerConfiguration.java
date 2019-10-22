package ru.otus.hw.webserver.configuration;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.security.*;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.security.authentication.FormAuthenticator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.server.session.DefaultSessionCache;
import org.eclipse.jetty.server.session.NullSessionDataStore;
import org.eclipse.jetty.server.session.SessionHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.security.Constraint;
import ru.otus.hw.webserver.filter.SecurityFilter;
import ru.otus.hw.webserver.service.UserService;
import ru.otus.hw.webserver.servlet.UserServlet;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;

@Slf4j
public class ServerConfiguration {

    private static final int PORT = 8080;
    private static final String LOGIN_PAGE = "/login_page.html";
    private static final String ERROR_PAGE = LOGIN_PAGE + "?error=true";

    public static Server initialServer(UserService userService) {
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

    private static ServletContextHandler createHandler(UserService userService) {
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);

        contextHandler.addServlet(new ServletHolder(new UserServlet(userService)), "/users");
        contextHandler.addFilter(new FilterHolder(new SecurityFilter()), "/*", null);

        return contextHandler;
    }

    private static SecurityHandler createSecurityHandler(ServletContextHandler contextHandler) {
        Constraint constraint = new Constraint();
        constraint.setName("auth");
        constraint.setAuthenticate(true);
        constraint.setRoles(new String[]{"ADMIN"});

        ConstraintMapping constraintMapping = new ConstraintMapping();
        constraintMapping.setPathSpec("/users/*");
        constraintMapping.setConstraint(constraint);

        ConstraintSecurityHandler securityHandler = new ConstraintSecurityHandler();
//        securityHandler.setAuthenticator(new FormAuthenticator(LOGIN_PAGE, ERROR_PAGE, false));
        securityHandler.setAuthenticator(new BasicAuthenticator());

        URL resource = ServerConfiguration.class.getResource("/realm.properties");
        if (resource == null) {
            log.error("realm.properties not found!");
            throw new RuntimeException("realm.properties not found!");
        }

//        securityHandler.setLoginService(new JDBCLoginService("jdbcRealm", resource.getPath()));
        securityHandler.setLoginService(new HashLoginService("jdbcRealm", resource.getPath()));
        securityHandler.setHandler(new HandlerList(contextHandler));
        securityHandler.setConstraintMappings(Collections.singletonList(constraintMapping));

        return securityHandler;
    }

    private static ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
//        resourceHandler.setWelcomeFiles(new String[]{"index.html"});

        URL fileDir = ServerConfiguration.class.getClassLoader().getResource("static");
        if (fileDir == null) {
            throw new RuntimeException("File Directory not found");
        }
        resourceHandler.setResourceBase(fileDir.getPath());
        return resourceHandler;
    }

}
