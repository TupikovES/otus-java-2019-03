package ru.otus.hw.orm;

import ru.otus.hw.orm.core.DbExecutorImpl;
import ru.otus.hw.orm.core.H2ConnectionManager;
import ru.otus.hw.orm.core.JdbcTemplate;
import ru.otus.hw.orm.core.JdbcTemplateImpl;
import ru.otus.hw.orm.entity.Account;
import ru.otus.hw.orm.entity.Person;

import java.math.BigDecimal;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws Exception {
        H2ConnectionManager connectionManager = new H2ConnectionManager();
        DbExecutorImpl executor = new DbExecutorImpl(connectionManager);
        JdbcTemplate jdbcTemplate = new JdbcTemplateImpl(executor);
        jdbcTemplate.create(
                Person.builder()
                        .username("test")
                        .age(24)
                        .build()
        );
        jdbcTemplate.create(
                Account.builder()
                        .type("user_acc")
                        .rest(new BigDecimal(2))
                        .build()
        );
        Optional<Person> person = jdbcTemplate.findById(1L, Person.class);
        Optional<Account> account = jdbcTemplate.findById(1L, Account.class);
        person.ifPresent(System.out::println);
        account.ifPresent(System.out::println);
        connectionManager.close();
    }

}
