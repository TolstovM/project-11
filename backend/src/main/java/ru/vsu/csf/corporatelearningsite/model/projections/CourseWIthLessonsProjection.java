package ru.vsu.csf.corporatelearningsite.model.projections;

import org.springframework.data.rest.core.config.Projection;
import ru.vsu.csf.corporatelearningsite.model.Course;
import ru.vsu.csf.corporatelearningsite.model.Lesson;

import java.util.List;

@Projection(name = "courseWithLessonsProjection", types = {Course.class})
public interface CourseWIthLessonsProjection {
    Long getId();
    String getName();
    String getDescription();
    List<LessonProjection> getLessons();
}
