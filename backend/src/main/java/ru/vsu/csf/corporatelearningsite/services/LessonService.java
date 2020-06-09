package ru.vsu.csf.corporatelearningsite.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.csf.corporatelearningsite.model.Lesson;
import ru.vsu.csf.corporatelearningsite.model.Material;
import ru.vsu.csf.corporatelearningsite.repositories.CommentRepository;
import ru.vsu.csf.corporatelearningsite.repositories.CourseRepository;
import ru.vsu.csf.corporatelearningsite.repositories.LessonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service(value = "lessonService")
@Transactional
public class LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final MaterialService materialService;
    private final HomeworkService homeworkService;

    @Autowired
    public LessonService(LessonRepository lessonRepository,
                         CourseRepository courseRepository,
                         MaterialService materialService,
                         HomeworkService homeworkService) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
        this.materialService = materialService;
        this.homeworkService = homeworkService;
    }

    public void add(Lesson lesson, Long courseId) {
        courseRepository.findById(courseId);
        if(courseRepository.findById(courseId).isPresent()) {
            lesson.setCourse(courseRepository.findById(courseId).get());
            lessonRepository.save(lesson);
        }
        log.info("IN add - lesson with id: {} successfully add", lesson.getId());
    }

    public Optional<Lesson> get(Long id) {
        log.info("IN get - lesson with id: {} successfully get", id);
        return lessonRepository.findById(id);
    }

    public void delete(Long id){
        homeworkService.deleteByLessonId(id);
        materialService.deleteByLessonId(id);
        lessonRepository.deleteById(id);
    }
}
