package ru.otus.hw.springdi.configuration;

import org.springframework.web.context.annotation.ApplicationScope;

import javax.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;

@BasicAuthenticationMechanismDefinition(realmName = "basicRealm")
@ApplicationScope
public class AppConfiguration {
}
