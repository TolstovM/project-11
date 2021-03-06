package ru.vsu.csf.corporatelearningsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.csf.corporatelearningsite.model.Homework;
import ru.vsu.csf.corporatelearningsite.model.HomeworkId;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RepositoryRestResource
public interface HomeworkRepository extends JpaRepository<Homework, HomeworkId> {

    @RestResource
    Optional<Homework> findById(HomeworkId id);

    @RestResource
    List<Homework> findAllByLessonId(Long id);

    @RestResource(exported = false)
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(
        value = "SELECT * FROM homeworks h WHERE h.lesson_id = ?1",
        nativeQuery = true)
    Collection<Homework> findAllByLesson(Long lessonId);

    Homework findHomeworkById(HomeworkId homeworkId);

    @RestResource(exported = false)
    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(
        value = "update homeworks h set h.is_passed=?3 where h.user_id=?1 and h.lesson_id=?2",
        nativeQuery = true)
    void checkHomework(String userId, Long lessonId, Boolean result);
}
