package ru.otus.hw.orm;

import lombok.extern.slf4j.Slf4j;
import ru.otus.hw.orm.core.DbExecutorImpl;
import ru.otus.hw.orm.core.H2ConnectionManager;
import ru.otus.hw.orm.core.Person;

import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        H2ConnectionManager connectionManager = new H2ConnectionManager();
        DbExecutorImpl executor = new DbExecutorImpl(connectionManager);
        Long id = (Long) executor.insert("insert into Person (username) values (?)", Collections.singletonList("TEST"));
        System.out.println(id);
        List<Person> personList = executor.select("select * from Person where id = ?",
                Collections.singletonList(id), resultSet ->
                        Person.builder()
                                .id(resultSet.getLong("id"))
                                .username(resultSet.getString("username"))
                                .build()
        );
        System.out.println(personList);
        connectionManager.close();
    }

}
