package ru.vsu.csf.corporatelearningsite.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.type.UUIDBinaryType;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User {

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Email
    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(
            name="user_role",
            joinColumns = { @JoinColumn(name="user_id") },
            inverseJoinColumns = {@JoinColumn(name = "role_id") }
            )
    private Set<Role> roles;

    @JsonIgnore
    @ManyToMany(mappedBy = "instructors")
    private List<Course> courses = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "listener", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ListenerOnCourse> onCourses;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Homework> homeworkList;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private Set<Comment> comments;

    public User(UUID id, String email, String name, String password, Set<Role> roles) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }
}
