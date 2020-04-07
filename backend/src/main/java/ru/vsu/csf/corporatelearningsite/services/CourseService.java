package ru.vsu.csf.corporatelearningsite.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.csf.corporatelearningsite.model.Course;
import ru.vsu.csf.corporatelearningsite.model.Lesson;
import ru.vsu.csf.corporatelearningsite.model.User;
import ru.vsu.csf.corporatelearningsite.repositories.CourseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service(value = "courseService")
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAll() {
        List<Course> list = new ArrayList<>();
        courseRepository.findAll().iterator().forEachRemaining(list::add);
        log.info("IN findAll - courses successfully find");
        return list;
    }

    public void add(Course course) {
        List<User> users = new ArrayList<>();
        courseRepository.save(course);
        log.info("IN add - course with id: {} successfully add", course.getId());
    }

    public void delete(Long id) {
        courseRepository.deleteById(id);
        log.info("IN delete - course with id: {} successfully delete", id);
    }

    public Optional<Course> get(String name) {
        log.info("IN get - course with name: {} successfully get", name);
        return courseRepository.findByName(name);
    }

    public List<Lesson> getLessons(String name) {
        log.info("IN get - course with name: {} successfully get", name);
        if(courseRepository.findByName(name).isPresent()) {
            return courseRepository.findByName(name).get().getLessons();
        }
        else
            return new ArrayList<>();
    }
}
