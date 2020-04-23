package ru.vsu.csf.corporatelearningsite.model.projections;

import org.springframework.data.rest.core.config.Projection;
import ru.vsu.csf.corporatelearningsite.model.Role;
import ru.vsu.csf.corporatelearningsite.model.User;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Projection(name = UserWithRolesProjection.INLINE_USER_WITH_ROLES, types = User.class)
public interface UserWithRolesProjection {
    String INLINE_USER_WITH_ROLES = "inlineUserWithRoles";

    UUID getId();
    String getName();
    String getEmail();
    Set<Role> getRoles();
}
