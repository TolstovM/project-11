package ru.vsu.csf.corporatelearningsite.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "homeworks")
public class Homework {

    @EmbeddedId
    private HomeworkId id;

    @Column(nullable = true)
    private String file;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("homeworkId")
    private HomeTask homeTask;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @OneToMany(mappedBy = "homework", fetch = FetchType.EAGER)
    private List<Comment> comments;
}
