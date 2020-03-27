package ru.vsu.csf.corporatelearningsite.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "listener_on_course")
public class ListenerOnCourse {

    @EmbeddedId
    private ListenerOnCourseId id;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean userMark;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("listenerId")
    private User listener;

}
