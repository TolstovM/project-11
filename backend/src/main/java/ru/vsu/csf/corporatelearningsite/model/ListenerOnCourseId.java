package ru.vsu.csf.corporatelearningsite.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class ListenerOnCourseId implements Serializable {

    @Column(name = "course_id")
    private Long courseId;

    @Column(name = "listener_Id")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID listenerId;

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public UUID getListenerId() {
        return listenerId;
    }

    public void setListenerId(UUID listenerId) {
        this.listenerId = listenerId;
    }

    public ListenerOnCourseId() {
    }

    public ListenerOnCourseId(Long courseId, UUID listenerId){
        this.courseId = courseId;
        this.listenerId = listenerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListenerOnCourseId that = (ListenerOnCourseId) o;
        return Objects.equals(courseId, that.courseId) &&
                Objects.equals(listenerId, that.listenerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, listenerId);
    }
}
