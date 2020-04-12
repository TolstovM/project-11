package ru.vsu.csf.corporatelearningsite.model.projections;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.rest.core.config.Projection;
import ru.vsu.csf.corporatelearningsite.model.Course;
import ru.vsu.csf.corporatelearningsite.model.projections.ListenerProjection;

import java.util.List;
import java.util.Set;

@Projection(name = "courseProjection", types = {Course.class})
public interface CourseProjection {
    Long getId();
    String getName();
    String getDescription();
}
