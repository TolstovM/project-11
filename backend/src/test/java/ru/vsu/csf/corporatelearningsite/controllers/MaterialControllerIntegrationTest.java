package ru.vsu.csf.corporatelearningsite.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.multipart.MultipartFile;
import ru.vsu.csf.corporatelearningsite.config.MaterialStorageProperties;
import ru.vsu.csf.corporatelearningsite.exceptions.MaterialStorageException;
import ru.vsu.csf.corporatelearningsite.model.Course;
import ru.vsu.csf.corporatelearningsite.model.Homework;
import ru.vsu.csf.corporatelearningsite.model.Lesson;
import ru.vsu.csf.corporatelearningsite.payload.UploadFileResponse;
import ru.vsu.csf.corporatelearningsite.repositories.*;
import ru.vsu.csf.corporatelearningsite.services.CourseService;
import ru.vsu.csf.corporatelearningsite.services.HomeworkService;
import ru.vsu.csf.corporatelearningsite.services.LessonService;
import ru.vsu.csf.corporatelearningsite.services.MaterialService;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Sql(value = {"classpath:scripts/dml-test-comment.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class MaterialControllerIntegrationTest {

    @Autowired
    MaterialRepository materialRepository;

    @Autowired
    LessonRepository lessonRepository;

    @Autowired
    MaterialStorageProperties materialStorageProperties;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ListenerOnCourseRepository listenerOnCourseRepository;

    @Autowired
    HomeworkService homeworkService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private final static String FILE_NAME = "file.txt";
    private final static String FILE_CONTENT_TYPE = "text/plain";
    private final static String FILE_CONTENT = "file content";
    private final static String BAD_FILE_NAME = "file..txt";
    private final static Long LESSON_ID = 1l;
    private final static Long COURSE_ID = 2l;
    private final static String NAME = "name";
    private final static String DESCRIPTION = "description";

    @AfterEach
    void tearDown() {
    }

    @Test
    void uploadMaterial() {

        MultipartFile file = new MockMultipartFile(FILE_NAME,
                FILE_NAME,
                FILE_CONTENT_TYPE,
                FILE_CONTENT.getBytes(StandardCharsets.UTF_8));

        MultipartFile fileWithBadPath = new MockMultipartFile(FILE_NAME,
                BAD_FILE_NAME,
                FILE_CONTENT_TYPE,
                FILE_CONTENT.getBytes(StandardCharsets.UTF_8));

        MaterialService materialService = new MaterialService(materialRepository, materialStorageProperties, lessonRepository);

        MaterialController materialController = new MaterialController(materialService);

        createLesson(materialService, createCourse());

        ResponseEntity<UploadFileResponse> uploadMaterial = materialController.uploadMaterial(LESSON_ID, file);
        assertTrue(uploadMaterial.getStatusCode().is2xxSuccessful());

        MaterialStorageException exception = assertThrows(MaterialStorageException.class, () -> materialService.storeMaterial(fileWithBadPath, LESSON_ID));
        assertEquals(exception.getMessage(), MaterialService.BAD_PATH_EXCEPTION_MASSAGE);
    }

    private Course createCourse(){
        CourseService courseService = new CourseService(courseRepository, userRepository, listenerOnCourseRepository);
        Course course = new Course(COURSE_ID);
        course.setName(NAME);
        course.setDescription(DESCRIPTION);
        course.setLessons(new ArrayList<>());
        course.setListeners(new ArrayList<>());
        courseService.add(course);
        return course;
    }

    private void createLesson(MaterialService materialService, Course course){
        LessonService lessonService = new LessonService(lessonRepository, courseRepository, materialService, homeworkService);
        Lesson lesson = new Lesson(LESSON_ID);
        lesson.setDescription(DESCRIPTION);
        lesson.setName(NAME);
        lesson.setHomeworks(new ArrayList<>());
        lesson.setCourse(course);
        lessonService.add(lesson, course.getId());
    }
}
