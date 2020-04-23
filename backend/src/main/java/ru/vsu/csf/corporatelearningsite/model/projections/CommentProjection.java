package ru.vsu.csf.corporatelearningsite.model.projections;

import org.springframework.data.rest.core.config.Projection;
import ru.vsu.csf.corporatelearningsite.model.Comment;

@Projection(name = "commentProjection", types = Comment.class)
public interface CommentProjection {

    Long getId();
    String getText();
    UserProjection getOwner();
}
