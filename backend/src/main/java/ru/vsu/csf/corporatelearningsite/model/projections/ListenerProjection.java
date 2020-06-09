package ru.vsu.csf.corporatelearningsite.model.projections;

import org.springframework.data.rest.core.config.Projection;
import ru.vsu.csf.corporatelearningsite.model.ListenerOnCourse;
import ru.vsu.csf.corporatelearningsite.model.ListenerOnCourseId;
import ru.vsu.csf.corporatelearningsite.model.User;

import java.util.UUID;

@Projection(name="listenerProjection", types = {ListenerOnCourse.class})
public interface ListenerProjection {
    ListenerOnCourseId getId();
    Boolean getUserMark();
    UserProjection getListener();
}
