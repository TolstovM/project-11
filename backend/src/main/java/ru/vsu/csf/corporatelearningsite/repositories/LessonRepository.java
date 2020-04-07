package ru.vsu.csf.corporatelearningsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.csf.corporatelearningsite.model.Lesson;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

}
