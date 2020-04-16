package ru.vsu.csf.corporatelearningsite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.csf.corporatelearningsite.exceptions.BadRequestException;
import ru.vsu.csf.corporatelearningsite.model.Homework;
import ru.vsu.csf.corporatelearningsite.model.HomeworkId;
import ru.vsu.csf.corporatelearningsite.model.Lesson;
import ru.vsu.csf.corporatelearningsite.model.User;
import ru.vsu.csf.corporatelearningsite.payload.CheckHomeworkRequest;
import ru.vsu.csf.corporatelearningsite.repositories.CourseRepository;
import ru.vsu.csf.corporatelearningsite.repositories.HomeworkRepository;
import ru.vsu.csf.corporatelearningsite.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HomeworkService {

    public static final String USER_DOES_NOT_LISTENER_ON_THAT_COURSE = "You are not a listener on than course";
    public static final String NO_RIGHTS = "You do not have enough rights to access this page";
    private HomeworkRepository homeworkRepository;
    private CourseRepository courseRepository;
    private UserRepository userRepository;

    @Autowired
    public HomeworkService(HomeworkRepository homeworkRepository, CourseRepository courseRepository, UserRepository userRepository) {
        this.homeworkRepository = homeworkRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
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

    public List<Homework> getHomeworksByLessonId(Long lessonId, UUID id) {
        if(courseRepository.isInstructorOnCourse(lessonId, id)){
            throw new BadRequestException(NO_RIGHTS);
        }
        else {
            return (List<Homework>) homeworkRepository.findAllByLesson(lessonId);
        }

    }

    public Homework getHomeworkById(HomeworkId homeworkId, UUID id) {
        if(courseRepository.isInstructorOnCourse(homeworkId.getLessonId(), id)){
            throw new BadRequestException(NO_RIGHTS);
        }
        else {
            return homeworkRepository.findHomeworkById(homeworkId);
        }
    }

    public void checkHomework(CheckHomeworkRequest checkHomeworkRequest, UUID id) {
        if(courseRepository.isInstructorOnCourse(checkHomeworkRequest.getLessonId(), id)){
            throw new BadRequestException(NO_RIGHTS);
        }
        else {
            homeworkRepository.checkHomework(checkHomeworkRequest.getUserId().toString(), checkHomeworkRequest.getLessonId(),
                checkHomeworkRequest.getResult());
        }
    }
}
