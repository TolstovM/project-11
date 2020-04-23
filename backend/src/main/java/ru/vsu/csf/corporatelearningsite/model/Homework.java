package ru.vsu.csf.corporatelearningsite.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "homeworks")
public class Homework {

    @EmbeddedId
    private HomeworkId id;

    @Column(nullable = true, columnDefinition = "boolean default false")
    private Boolean isPassed;

    @Column(nullable = true)
    private String file;

    @JsonIgnore
    @RestResource(exported=false)
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("homeworkId")
    private Lesson lesson;


    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    private User user;

    @OneToMany(mappedBy = "homework", fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

    public HomeworkId getId() {
        return id;
    }

    public void setId(HomeworkId id) {
        this.id = id;
    }

    public Boolean getPassed() {
        return isPassed;
    }

    public void setPassed(Boolean passed) {
        isPassed = passed;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Homework() {
    }

    public Homework(UUID userId, Long lessonId) {
        this.id = new HomeworkId(userId, lessonId);
    }

    public Homework(UUID userId, Long lessonId, User user, Lesson lesson) {
        this.id = new HomeworkId(userId, lessonId);
        this.user = user;
        this.lesson = lesson;
        this.isPassed = false;
    }

    @Override
    public String toString() {
        return "Homework{" +
                "id=" + id +
                ", file='" + file + '\'' +
                ", comments=" + comments +
                '}';
    }
}
