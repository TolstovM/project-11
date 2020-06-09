package ru.vsu.csf.corporatelearningsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.vsu.csf.corporatelearningsite.model.Lesson;
import java.util.Optional;

@RepositoryRestResource(path="lesson")
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    Optional<Lesson> findByName(String name);
}
