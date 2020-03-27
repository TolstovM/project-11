package ru.vsu.csf.corporatelearningsite.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "home_tasks")
public class HomeTask {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String description;

    @OneToOne(mappedBy = "homeTask")
    private Lesson lesson;

    @OneToMany(mappedBy = "homeTask", fetch = FetchType.LAZY)
    private List<Homework> homeworkList;
}
