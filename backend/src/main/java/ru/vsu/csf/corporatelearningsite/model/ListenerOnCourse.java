package ru.vsu.csf.corporatelearningsite.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;


@NoArgsConstructor
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

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("listenerId")
    private User listener;

    public ListenerOnCourse(Long courseId, UUID listenerId){
        this.userMark = false;
        this.id = new ListenerOnCourseId(courseId,listenerId);
    }



}
