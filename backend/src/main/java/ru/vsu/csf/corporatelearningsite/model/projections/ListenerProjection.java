package ru.vsu.csf.corporatelearningsite.model.projections;

import ru.vsu.csf.corporatelearningsite.model.User;

import java.util.UUID;

public interface ListenerProjection {
    Boolean getUserMark();
    UserWithRolesProjection getListener();
    //User getListener();
}
