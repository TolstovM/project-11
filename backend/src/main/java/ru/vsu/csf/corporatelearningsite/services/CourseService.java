package ru.vsu.csf.corporatelearningsite.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import ru.vsu.csf.corporatelearningsite.exceptions.BadRequestException;
import ru.vsu.csf.corporatelearningsite.exceptions.ResourceNotFoundException;
import ru.vsu.csf.corporatelearningsite.model.*;
import ru.vsu.csf.corporatelearningsite.payload.CreateCourseRequest;
import ru.vsu.csf.corporatelearningsite.repositories.CourseRepository;
import ru.vsu.csf.corporatelearningsite.repositories.ListenerOnCourseRepository;
import ru.vsu.csf.corporatelearningsite.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service(value = "courseService")
@Transactional
public class CourseService {

    public static final String SET_MARK_ACCESS_EXCEPTION_MESSAGE = "You have to be instructor on course with id: %s";
    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    private final ListenerOnCourseRepository listenerOnCourseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, UserRepository userRepository,
                         ListenerOnCourseRepository listenerOnCourseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.listenerOnCourseRepository = listenerOnCourseRepository;
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


    public void addListener(String email, String courseId) {
        Optional<User> user = userRepository.findByEmail(email);
        if(!user.isPresent())
            throw new ResourceNotFoundException("User", "email", email);
        else {
            Optional<Course> course = courseRepository.findById(Long.parseLong(courseId));
            if(!course.isPresent())
                throw new ResourceNotFoundException("Course", "courseId", courseId);
            else{
                ListenerOnCourse listenerOnCourse = new ListenerOnCourse(new ListenerOnCourseId(course.get().getId(), user.get().getId()), new Course(course.get().getId()), new User(user.get().getId()));
                listenerOnCourseRepository.save(listenerOnCourse);
            }

        }
    }


    @Transactional
    public void create(CreateCourseRequest request, UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        Course course = new Course(request.getName(), request.getDescription());
        course = courseRepository.save(course);
        course.insertInstructor(user);
        courseRepository.save(course);
    }

    public void setMark(UUID instructorId, Long courseId, UUID userId, boolean isPassed) {
        if (!courseRepository.isInstructorOnCourseByCourseId(courseId, instructorId)) {
            throw new BadRequestException(String.format(SET_MARK_ACCESS_EXCEPTION_MESSAGE, courseId));
        }
        ListenerOnCourseId listenerOnCourseId = new ListenerOnCourseId(courseId, userId);
        ListenerOnCourse listenerOnCourse = listenerOnCourseRepository.findById(listenerOnCourseId)
                .orElseThrow(() -> new ResourceNotFoundException("ListenerOnCourse", "id", listenerOnCourseId));
        listenerOnCourse.setUserMark(isPassed);
        listenerOnCourseRepository.save(listenerOnCourse);
    }

    public boolean isUserPassed(Long courseId, UUID id) {
        ListenerOnCourseId listenerOnCourseId = new ListenerOnCourseId(courseId, id);
        ListenerOnCourse listenerOnCourse = listenerOnCourseRepository.findById(listenerOnCourseId)
                .orElseThrow(() -> new ResourceNotFoundException("ListenerOnCourse", "id", listenerOnCourseId));
        return listenerOnCourse.getUserMark();
    }
}
