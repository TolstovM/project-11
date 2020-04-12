package ru.vsu.csf.corporatelearningsite.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.vsu.csf.corporatelearningsite.model.Course;
import ru.vsu.csf.corporatelearningsite.model.Lesson;
import ru.vsu.csf.corporatelearningsite.model.ListenerOnCourse;
import ru.vsu.csf.corporatelearningsite.model.ListenerOnCourseId;

import java.util.UUID;

public interface ListenerOnCourseRepository extends JpaRepository<ListenerOnCourse, ListenerOnCourseId> {
}
