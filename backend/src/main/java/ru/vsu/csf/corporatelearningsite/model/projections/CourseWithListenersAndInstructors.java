package ru.vsu.csf.corporatelearningsite.model.projections;

import org.springframework.data.rest.core.config.Projection;
import ru.vsu.csf.corporatelearningsite.model.Course;
import ru.vsu.csf.corporatelearningsite.model.User;

import java.util.List;

@Projection(name = CourseWithListenersAndInstructors.INLINE_COURSE_EAGER_PROJECTION, types = Course.class)
public interface CourseWithListenersAndInstructors {

    static String INLINE_COURSE_EAGER_PROJECTION = "inlineCourseEagerProjection";

    Long getId();
    String getName();
    String getDescription();
    List<User> getInstructors();
    List<User> getListeners();
}
