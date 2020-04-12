package ru.vsu.csf.corporatelearningsite.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class HomeworkId implements Serializable {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID userId;

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lesson_id")
    private Long lessonId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HomeworkId that = (HomeworkId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(lessonId, that.lessonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, lessonId);
    }
}
