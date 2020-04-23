package ru.vsu.csf.corporatelearningsite.model.projections;

import org.springframework.data.rest.core.config.Projection;
import ru.vsu.csf.corporatelearningsite.model.Lesson;
import ru.vsu.csf.corporatelearningsite.model.Material;

import java.util.List;

@Projection(name = LessonProjection.INLINE_LESSON, types = Lesson.class)
public interface LessonProjection {
    String INLINE_LESSON = "lessonProjection";

    Long getId();
    String getName();
    String getDescription();
}
