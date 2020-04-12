package ru.vsu.csf.corporatelearningsite.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "homeworks")
public class Homework {

    @EmbeddedId
    private HomeworkId id;

    @Column(nullable = true)
    private String file;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("homeworkId")
    private Lesson lesson;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @OneToMany(mappedBy = "homework", fetch = FetchType.EAGER)
    private List<Comment> comments;

    public Homework(UUID userId, Long lessonId) {
        this.id = new HomeworkId(userId, lessonId);
    }

    public Homework(UUID userId, Long lessonId, User user, Lesson lesson) {
        this.id = new HomeworkId(userId, lessonId);
        this.user = user;
        this.lesson = lesson;
    }
}
