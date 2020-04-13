
package ru.vsu.csf.corporatelearningsite.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.UUID;


@Entity
@Table(name = "listener_on_course")
public class ListenerOnCourse {

    @EmbeddedId
    private ListenerOnCourseId id;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean userMark;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("courseId")
    private Course course;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("listenerId")
    private User listener;

    public ListenerOnCourseId getId() {
        return id;
    }

    public void setId(ListenerOnCourseId id) {
        this.id = id;
    }

    public Boolean getUserMark() {
        return userMark;
    }

    public void setUserMark(Boolean userMark) {
        this.userMark = userMark;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getListener() {
        return listener;
    }

    public void setListener(User listener) {
        this.listener = listener;
    }

    public ListenerOnCourse(Long courseId, UUID listenerId){
        this.userMark = false;
        this.id = listenerOnCourseId;
        this.course = course;
        this.listener = user;
    }

    public ListenerOnCourse() {
    }
}
