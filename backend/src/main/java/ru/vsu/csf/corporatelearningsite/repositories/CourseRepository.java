package ru.vsu.csf.corporatelearningsite.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.vsu.csf.corporatelearningsite.model.Course;
import ru.vsu.csf.corporatelearningsite.model.projections.CourseProjection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RepositoryRestResource(collectionResourceRel = "courses", path = "course", excerptProjection=CourseProjection.class)
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByName(String name);

    @RestResource()
    @Query("select c from Course c inner join ListenerOnCourse lc on lc.id.courseId = c.id where lc.id.listenerId=:uuid")
    Page<Course> findAllByUserId(@Param("uuid") UUID uuid,  Pageable pageable);

    @RestResource(exported = false)
    @Query("select case when count(c)>0 then true else false end from Course c " +
            "inner join Lesson l on c.id = l.course.id inner join ListenerOnCourse lc on c.id = lc.course.id " +
            "where lc.listener.id=:userId and l.id=:lessonId")
    Boolean isUserOnCourse(@Param("lessonId") Long lessonId, @Param("userId") UUID userId);

    @RestResource(exported = false)
    @Query("select case when count(l)>0 then true else false end from Lesson l " +
            "inner join l.course c inner join  c.instructors i where i.id=:instructorId " +
            "and l.id=:lessonId")
    Boolean isInstructorOnCourse(@Param("lessonId") Long lessonId, @Param("instructorId") UUID instructorId);

    @Override
    List<Course> findAll();
}
