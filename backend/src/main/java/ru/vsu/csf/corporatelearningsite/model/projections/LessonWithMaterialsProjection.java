package ru.vsu.csf.corporatelearningsite.model.projections;

import org.springframework.data.rest.core.config.Projection;
import ru.vsu.csf.corporatelearningsite.model.Lesson;
import ru.vsu.csf.corporatelearningsite.model.Material;

import java.util.List;

@Projection(name="lessonWithMaterialProjection", types = {Lesson.class})
public interface LessonWithMaterialsProjection {
    Long getId();
    String getName();
    String getDescription();
    List<Material> getMaterials();
}
