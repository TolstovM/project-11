package ru.vsu.csf.corporatelearningsite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.csf.corporatelearningsite.exceptions.BadRequestException;
import ru.vsu.csf.corporatelearningsite.model.Homework;
import ru.vsu.csf.corporatelearningsite.model.Lesson;
import ru.vsu.csf.corporatelearningsite.model.User;
import ru.vsu.csf.corporatelearningsite.repositories.CourseRepository;
import ru.vsu.csf.corporatelearningsite.repositories.HomeworkRepository;

import java.util.Optional;
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

    public Optional<Homework> findOrCreate(Long lessonId, UUID userId) {
        if (!courseRepository.isUserOnCourse(lessonId, userId)) {
            throw new BadRequestException(USER_DOES_NOT_LISTENER_ON_THAT_COURSE);
        }
        Homework homework = new Homework(userId, lessonId, new User(userId), new Lesson(lessonId));
        Optional<Homework> homeworkOptional = this.homeworkRepository.findById(homework.getId());
        if (homeworkOptional.isPresent()) {
            return homeworkOptional;
        } else {
            return Optional.of(this.homeworkRepository.save(homework));
        }
    }
}
