package ru.vsu.csf.corporatelearningsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import ru.vsu.csf.corporatelearningsite.model.Course;
import ru.vsu.csf.corporatelearningsite.model.User;
import ru.vsu.csf.corporatelearningsite.model.projections.CourseWithListenersAndInstructors;
import ru.vsu.csf.corporatelearningsite.model.projections.UserWithRolesProjection;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RepositoryRestResource(collectionResourceRel = "courses", path = "course", excerptProjection = CourseWithListenersAndInstructors.class)
public interface CourseRepository extends JpaRepository<Course, Long> {
    Optional<Course> findByName(String name);

    @RestResource()
    @Query("select c from Course c inner join ListenerOnCourse lc on lc.id.courseId = c.id where lc.id.listenerId=:uuid")
    List<Course> findAllByUserId(@Param("uuid") UUID uuid);
}
