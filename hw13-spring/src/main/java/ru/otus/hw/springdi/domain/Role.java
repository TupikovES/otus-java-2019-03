package ru.otus.hw.springdi.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import ru.otus.hw.hibernate.domain.AbstractEntity;

import javax.persistence.*;

@Entity
@Data
@Builder
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Role")
public class Role extends AbstractEntity {

    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    @Column(name = "id")
    private Long id;

    @Column(name = "role")
    private String role;

}
