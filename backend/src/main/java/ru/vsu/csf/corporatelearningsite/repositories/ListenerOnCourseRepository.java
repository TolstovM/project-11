package ru.vsu.csf.corporatelearningsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import ru.vsu.csf.corporatelearningsite.model.Course;
import ru.vsu.csf.corporatelearningsite.model.Lesson;
import ru.vsu.csf.corporatelearningsite.model.ListenerOnCourse;
import ru.vsu.csf.corporatelearningsite.model.ListenerOnCourseId;

import java.util.UUID;

@RepositoryRestResource(path="listener")
public interface ListenerOnCourseRepository extends JpaRepository<ListenerOnCourse, ListenerOnCourseId> {
}
