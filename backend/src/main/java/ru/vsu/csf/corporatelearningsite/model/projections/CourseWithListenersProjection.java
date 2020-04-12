package ru.vsu.csf.corporatelearningsite.model.projections;

import org.springframework.data.rest.core.config.Projection;
import ru.vsu.csf.corporatelearningsite.model.Course;

import java.util.List;

@Projection(name = "courseWithListenersProjection", types = {Course.class})
public interface CourseWithListenersProjection {
    Long getId();
    String getName();
    String getDescription();
    List<ListenerProjection> getListeners();
}
