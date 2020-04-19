package ru.vsu.csf.corporatelearningsite.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.csf.corporatelearningsite.config.HomeworkStorageProperties;
import ru.vsu.csf.corporatelearningsite.exceptions.BadRequestException;
import ru.vsu.csf.corporatelearningsite.exceptions.HomeworkStorageException;
import ru.vsu.csf.corporatelearningsite.exceptions.ResourceNotFoundException;
import ru.vsu.csf.corporatelearningsite.model.*;
import ru.vsu.csf.corporatelearningsite.repositories.*;
import ru.vsu.csf.corporatelearningsite.security.user.UserPrincipal;
import ru.vsu.csf.corporatelearningsite.model.Homework;
import ru.vsu.csf.corporatelearningsite.model.HomeworkId;
import ru.vsu.csf.corporatelearningsite.model.Lesson;
import ru.vsu.csf.corporatelearningsite.model.User;
import ru.vsu.csf.corporatelearningsite.payload.CheckHomeworkRequest;
import ru.vsu.csf.corporatelearningsite.repositories.CourseRepository;
import ru.vsu.csf.corporatelearningsite.repositories.HomeworkRepository;
import ru.vsu.csf.corporatelearningsite.repositories.UserRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class HomeworkService {

    private static final String USER_DOES_NOT_LISTENER_ON_THAT_COURSE = "You are not a listener on than course";
    private static final String NO_RIGHTS = "You do not have enough rights to access this page";
    private HomeworkRepository homeworkRepository;
    private CourseRepository courseRepository;
    private UserRepository userRepository;
    private static final String RESOURCE_NAME = "Homework";
    private static final String FIELD_NAME_ID = "id";
    private final LessonRepository lessonRepository;
    private final Path homeworkStorageLocation;

    @Autowired
    public HomeworkService(HomeworkRepository homeworkRepository,
                           CourseRepository courseRepository,
                           UserRepository userRepository,
                           LessonRepository lessonRepository,
                           HomeworkStorageProperties homeworkStorageProperties) {
        this.homeworkRepository = homeworkRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.lessonRepository = lessonRepository;

        this.homeworkStorageLocation = Paths.get(homeworkStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.homeworkStorageLocation);
        } catch (IOException ex) {
            log.error("Could not create the directory {} where the uploaded homework will be stored",
                    homeworkStorageProperties.getUploadDir());
            throw new HomeworkStorageException("Could not create the directory where the uploaded homework will be stored.", ex);
        }
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
        if(!courseRepository.isInstructorOnCourse(lessonId, id)){
            throw new BadRequestException(NO_RIGHTS);
        }
        else {
            return (List<Homework>) homeworkRepository.findAllByLesson(lessonId);
        }

    }

    public Homework getHomeworkById(HomeworkId homeworkId, UUID id) {
        if(!courseRepository.isInstructorOnCourse(homeworkId.getLessonId(), id)){
            throw new BadRequestException(NO_RIGHTS);
        }
        else {
            return homeworkRepository.findHomeworkById(homeworkId);
        }
    }

    public String storeHomework(MultipartFile homework, String lessonName, UserPrincipal userPrincipal) {
        String homeworkName = StringUtils.cleanPath(homework.getOriginalFilename());
        User user;
        if( userRepository.findByEmail(userPrincipal.getEmail()).isPresent())
            user = userRepository.findByEmail(userPrincipal.getEmail()).get();
        else
            throw new HomeworkStorageException("User email not found");
        try {
            if (homeworkName.contains("..")) {
                log.error("Filename contains invalid path sequence {}", homeworkName);
                throw new HomeworkStorageException("Sorry! Filename contains invalid path sequence " + homeworkName);
            }

            Homework dbHomework;
            if(lessonRepository.findByName(lessonName).isPresent())
                dbHomework = new Homework(user.getId(), lessonRepository.findByName(lessonName).get().getId(),
                        user, lessonRepository.findByName(lessonName).get());
            else
                throw new HomeworkStorageException("Lesson name not found");

            Path targetLocation = this.homeworkStorageLocation.resolve(homeworkName);
            Files.copy(homework.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            dbHomework.setFile(homeworkName);
            homeworkRepository.save(dbHomework);
            return homework.getName();
        } catch (IOException ex) {
            log.error("Could not store homework {}", homeworkName);
            throw new HomeworkStorageException("Could not store homework " + homeworkName + ". Please try again!", ex);
        }
    }

    public Resource loadHomeworkAsResource(String homeworkName) {
        try {
            Path homeworkPath = this.homeworkStorageLocation.resolve(homeworkName).normalize();
            Resource resource = new UrlResource(homeworkPath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                log.error("Homework not found {}", homeworkName);
                throw new ResourceNotFoundException(RESOURCE_NAME, FIELD_NAME_ID, homeworkName);
            }
        } catch (MalformedURLException ex) {
            log.error("Homework not found {}", homeworkName);
            throw new ResourceNotFoundException(RESOURCE_NAME, FIELD_NAME_ID, homeworkName);
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
