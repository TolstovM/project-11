package ru.vsu.csf.corporatelearningsite.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "homeworks")
public class Homework {

    @EmbeddedId
    private HomeworkId id;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean isPassed;

    @Column(nullable = true)
    private String file;

    @JsonIgnore
    @RestResource(exported=false)
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("homeworkId")
    private Lesson lesson;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("userId")
    private User user;

    @OneToMany(mappedBy = "homework", fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

    public Homework(UUID userId, Long lessonId) {
        this.id = new HomeworkId(userId, lessonId);
    }

    public Homework(UUID userId, Long lessonId, User user, Lesson lesson) {
        this.id = new HomeworkId(userId, lessonId);
        this.user = user;
        this.lesson = lesson;
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
