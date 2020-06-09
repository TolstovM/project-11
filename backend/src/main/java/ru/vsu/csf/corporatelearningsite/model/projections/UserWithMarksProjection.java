package ru.vsu.csf.corporatelearningsite.model.projections;

import org.springframework.data.rest.core.config.Projection;
import ru.vsu.csf.corporatelearningsite.model.ListenerOnCourse;
import ru.vsu.csf.corporatelearningsite.model.User;

import java.util.List;
import java.util.UUID;

@Projection(name = "userWithMarksProjection", types = {User.class})
public interface UserWithMarksProjection {
    UUID getId();
    String getEmail();
    String getName();
    List<ListenerProjection> getOnCourses();
}
