package ru.otus.hw.hibernate.domain;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Data
@Builder
@Table(name = "Phone", uniqueConstraints = {@UniqueConstraint(name = "uk_phone_number", columnNames = "number")})
public class Phone {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "number", nullable = false)
    private String number;

}
