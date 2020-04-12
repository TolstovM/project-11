package ru.vsu.csf.corporatelearningsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import ru.vsu.csf.corporatelearningsite.model.Lesson;
import ru.vsu.csf.corporatelearningsite.model.projections.LessonProjection;

@RepositoryRestResource(path="lesson", excerptProjection=LessonProjection.class)
public interface LessonRepository extends JpaRepository<Lesson, Long> {

}
