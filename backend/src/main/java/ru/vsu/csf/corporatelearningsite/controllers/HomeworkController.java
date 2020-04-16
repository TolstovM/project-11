package ru.vsu.csf.corporatelearningsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.corporatelearningsite.model.Homework;
import ru.vsu.csf.corporatelearningsite.model.HomeworkId;
import ru.vsu.csf.corporatelearningsite.payload.ApiResponse;
import ru.vsu.csf.corporatelearningsite.payload.CheckHomeworkRequest;
import ru.vsu.csf.corporatelearningsite.security.user.UserPrincipal;
import ru.vsu.csf.corporatelearningsite.services.HomeworkService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        Optional<Homework> homework = this.homeworkService.findOrCreate(lessonId, userPrincipal.getId());
        return ResponseEntity.ok(homework);
    }

    @GetMapping("/findAll/{lessonId}")
    public ResponseEntity<?> findAllHomeworks(@PathVariable("lessonId") Long lessonId,
                                              @AuthenticationPrincipal Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        List<Homework> result = this.homeworkService.getHomeworksByLessonId(lessonId, userPrincipal.getId());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/findHomework")
    public ResponseEntity<?> findHomework(@RequestParam("lessonId") Long lessonId, @RequestParam("userId") UUID userId,
                                          @AuthenticationPrincipal Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Homework result = this.homeworkService.getHomeworkById(new HomeworkId(userId, lessonId), userPrincipal.getId());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/checkHomework")
    public ResponseEntity<?> checkHomework(@RequestBody CheckHomeworkRequest checkHomeworkRequest,
                                           @AuthenticationPrincipal Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        this.homeworkService.checkHomework(checkHomeworkRequest, userPrincipal.getId());
        return ResponseEntity.ok(new ApiResponse(true, "Homework successfully changed"));
    }
}
