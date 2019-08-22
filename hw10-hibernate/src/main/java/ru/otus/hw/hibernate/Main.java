package ru.otus.hw.hibernate;

import ru.otus.hw.hibernate.core.ConnectionManager;
import ru.otus.hw.hibernate.core.H2ConnectionManager;
import ru.otus.hw.hibernate.dao.JdbcTemplate;
import ru.otus.hw.hibernate.dao.JdbcTemplateImpl;
import ru.otus.hw.hibernate.domain.Address;
import ru.otus.hw.hibernate.domain.Phone;
import ru.otus.hw.hibernate.domain.User;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Main {

    public static void main(String[] args) {
        ConnectionManager connectionManager = new H2ConnectionManager();
        EntityManager entityManager = connectionManager.getEntityManager();
        JdbcTemplate template = new JdbcTemplateImpl(entityManager);

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

        template.createOrUpdate(user_1);

        Optional<User> userOptional = template.findById(1L, User.class);
        userOptional.ifPresent(System.out::println);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Address address = user.getAddress();
            address.setStreet("new_street");
            template.update(user);
            template.findById(1L, User.class).ifPresent(System.out::println);
        }
    }
}
