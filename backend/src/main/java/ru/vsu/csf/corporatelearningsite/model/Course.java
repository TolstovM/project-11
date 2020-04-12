package ru.vsu.csf.corporatelearningsite.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @JsonManagedReference
    @RestResource(exported = true)
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "course_instructor",
            joinColumns = { @JoinColumn(name = "course_id", updatable = false) },
            inverseJoinColumns = { @JoinColumn(name = "instructor_id", updatable = false) }
    )
    private Set<User> instructors;

    @JsonIgnore
    @RestResource(exported=true)
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<ListenerOnCourse> listeners;


    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Lesson> lessons;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<User> getInstructors() {
        return instructors;
    }

    public void setInstructors(Set<User> instructors) {
        this.instructors = instructors;
    }

    public List<ListenerOnCourse> getListeners() {
        return listeners;
    }

    public void setListeners(List<ListenerOnCourse> listeners) {
        this.listeners = listeners;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public Course() {
    }

    public void insertInstructor(User instructor) {
        if (this.instructors == null) {
            this.instructors = new HashSet<>();
        }
        this.instructors.add(instructor);
    }

    public void deleteInstructor(User instructor) {
        if (this.instructors == null) {
            return;
        }
        this.instructors.remove(instructor);
    }
}
