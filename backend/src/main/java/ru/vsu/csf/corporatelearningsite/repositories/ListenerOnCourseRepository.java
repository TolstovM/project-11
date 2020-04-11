package ru.vsu.csf.corporatelearningsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.vsu.csf.corporatelearningsite.model.Course;
import ru.vsu.csf.corporatelearningsite.model.ListenerOnCourse;
import ru.vsu.csf.corporatelearningsite.model.ListenerOnCourseId;

public interface ListenerOnCourseRepository extends JpaRepository<ListenerOnCourse, ListenerOnCourseId> {

}
