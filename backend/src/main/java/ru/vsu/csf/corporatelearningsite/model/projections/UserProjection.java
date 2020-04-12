package ru.vsu.csf.corporatelearningsite.model.projections;

import org.springframework.data.rest.core.config.Projection;
import ru.vsu.csf.corporatelearningsite.model.User;

import java.util.UUID;

@Projection(name = "userProjection", types = {User.class})
public interface UserProjection {
    UUID getId();
    String getEmail();
    String getName();
}
