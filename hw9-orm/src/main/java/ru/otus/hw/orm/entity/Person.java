package ru.otus.hw.orm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw.orm.core.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    @Id
    private Long id;
    private String username;
    private Integer age;

}
