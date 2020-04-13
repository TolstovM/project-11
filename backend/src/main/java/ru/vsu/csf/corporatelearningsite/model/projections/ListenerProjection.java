package ru.vsu.csf.corporatelearningsite.model.projections;

import org.springframework.data.rest.core.config.Projection;
import ru.vsu.csf.corporatelearningsite.model.ListenerOnCourse;
import ru.vsu.csf.corporatelearningsite.model.User;

import java.util.UUID;

@Projection(name="listenerProjection", types = {ListenerOnCourse.class})
public interface ListenerProjection {
    Boolean getUserMark();
    UserProjection getListener();
}
