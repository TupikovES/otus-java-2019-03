package ru.otus.hw.orm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw.orm.core.Id;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    private Long id;
    private String type;
    private BigDecimal rest;

}
