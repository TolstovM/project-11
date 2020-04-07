package ru.vsu.csf.corporatelearningsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.corporatelearningsite.model.Lesson;
import ru.vsu.csf.corporatelearningsite.services.LessonService;

@RestController
@RequestMapping("/lesson")
public class LessonController {

    private final LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping("/{courseName}")
    public ResponseEntity<Lesson> addLesson(@PathVariable("courseName") String courseName, @RequestBody Lesson lesson) {
        lessonService.add(lesson, courseName);
        return ResponseEntity.ok().build();
    }
}
