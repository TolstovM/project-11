package ru.vsu.csf.corporatelearningsite.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.type.UUIDBinaryType;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.*;

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

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(
            name="user_role",
            joinColumns = { @JoinColumn(name="user_id") },
            inverseJoinColumns = {@JoinColumn(name = "role_id") }
            )
    private Set<Role> roles;


    @RestResource(exported = true)
    @ManyToMany(mappedBy = "instructors")
    @JsonBackReference
    private Set<Course> courses;

    @JsonIgnore
    @OneToMany(mappedBy = "listener", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ListenerOnCourse> onCourses;

    @JsonIgnore
    @RestResource(exported=false)
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private Set<Homework> homeworkList;

    @JsonIgnore
    @RestResource(exported=false)
    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private Set<Comment> comments;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Set<ListenerOnCourse> getOnCourses() {
        return onCourses;
    }

    public void setOnCourses(Set<ListenerOnCourse> onCourses) {
        this.onCourses = onCourses;
    }

    public Set<Homework> getHomeworkList() {
        return homeworkList;
    }

    public void setHomeworkList(Set<Homework> homeworkList) {
        this.homeworkList = homeworkList;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public User() {
    }

    public User(UUID id, String email, String name, String password, Set<Role> roles) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.roles = roles;
    }

    public User(UUID id) {
        this.id = id;
    }

    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
