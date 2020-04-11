package ru.vsu.csf.corporatelearningsite.model.projections;

import org.springframework.data.rest.core.config.Projection;
import ru.vsu.csf.corporatelearningsite.model.Lesson;
import ru.vsu.csf.corporatelearningsite.model.Material;

import java.util.List;

@Projection(name = LessonProjection.INLINE_LESSON, types = Lesson.class)
public interface LessonProjection {
    String INLINE_LESSON = "inlineLesson";

    Long getId();
    String getName();
    String getDescription();
    List<Material> getMaterials();
}
