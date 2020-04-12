package ru.vsu.csf.corporatelearningsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.vsu.csf.corporatelearningsite.model.Lesson;
import ru.vsu.csf.corporatelearningsite.model.projections.LessonProjection;

@CrossOrigin(origins = "http://localhost:4200")
@RepositoryRestResource(path="lesson")
public interface LessonRepository extends JpaRepository<Lesson, Long> {

}
