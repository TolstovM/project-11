package ru.vsu.csf.corporatelearningsite.model.projections;

import org.springframework.data.rest.core.config.Projection;
import ru.vsu.csf.corporatelearningsite.model.Homework;
import ru.vsu.csf.corporatelearningsite.model.HomeworkId;

import java.util.List;

@Projection(name="homeworkProjection", types = {Homework.class})
public interface HomeworkProjection {

    HomeworkId getId();
    Boolean getIsPassed();
    String getFile();
    UserProjection getUser();
    List<CommentProjection> getComments();
}
