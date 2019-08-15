package ru.otus.hw.orm.core;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Person {

    @Id
    private Long id;
    private String username;

}
