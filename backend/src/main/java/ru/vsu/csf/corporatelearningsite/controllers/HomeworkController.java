package ru.vsu.csf.corporatelearningsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.csf.corporatelearningsite.model.Homework;
import ru.vsu.csf.corporatelearningsite.security.user.UserPrincipal;
import ru.vsu.csf.corporatelearningsite.services.HomeworkService;

@RestController
@RequestMapping(HomeworkController.API_HOMEWORK)
public class HomeworkController {

    public static final String API_HOMEWORK = "/api/homework";
    private HomeworkService homeworkService;

    @Autowired
    public HomeworkController(HomeworkService homeworkService) {
        this.homeworkService = homeworkService;
    }

    @GetMapping("/find/{lessonId}")
    public ResponseEntity<?> findOrCreate(@PathVariable("lessonId") Long lessonId,
                                          @AuthenticationPrincipal Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Homework homework = this.homeworkService.findOrCreate(lessonId, userPrincipal.getId());
        return ResponseEntity.ok(homework);
    }
}
