package ru.vsu.csf.corporatelearningsite.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AppRole name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    public Role(AppRole name) {
        this.name = name;
    }
}
