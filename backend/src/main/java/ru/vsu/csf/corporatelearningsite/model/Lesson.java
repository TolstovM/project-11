package ru.vsu.csf.corporatelearningsite.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "lesson")
public class Lesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "lesson", fetch = FetchType.EAGER)
    private List<Material> materials;

//    @JsonIgnore
//    @OneToMany(mappedBy = "lesson", fetch = FetchType.LAZY)
//    private List<Homework> homeworks;

    public Lesson(Long id) {
        this.id = id;
    }
}
