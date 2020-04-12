package ru.vsu.csf.corporatelearningsite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.csf.corporatelearningsite.exceptions.BadRequestException;
import ru.vsu.csf.corporatelearningsite.model.Homework;
import ru.vsu.csf.corporatelearningsite.model.HomeworkId;
import ru.vsu.csf.corporatelearningsite.model.Lesson;
import ru.vsu.csf.corporatelearningsite.model.User;
import ru.vsu.csf.corporatelearningsite.repositories.CourseRepository;
import ru.vsu.csf.corporatelearningsite.repositories.HomeworkRepository;
import ru.vsu.csf.corporatelearningsite.repositories.ListenerOnCourseRepository;

import java.util.UUID;

@Service
public class HomeworkService {

    public static final String USER_DOES_NOT_LISTENER_ON_THAT_COURSE = "You does not listener on than course";
    private HomeworkRepository homeworkRepository;
    private CourseRepository courseRepository;

    @Autowired
    public HomeworkService(HomeworkRepository homeworkRepository, CourseRepository courseRepository) {
        this.homeworkRepository = homeworkRepository;
        this.courseRepository = courseRepository;
    }

    public Homework findOrCreate(Long lessonId, UUID userId) {
        if (!courseRepository.isUserOnCourse(lessonId, userId)) {
            throw new BadRequestException(USER_DOES_NOT_LISTENER_ON_THAT_COURSE);
        }
        return this.homeworkRepository.findById(new HomeworkId(userId, lessonId))
                .orElse(this.homeworkRepository.save(new Homework(userId, lessonId, new User(userId), new Lesson(lessonId))));
    }
}
