package ru.vsu.csf.corporatelearningsite.controllers;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.corporatelearningsite.model.Course;
import ru.vsu.csf.corporatelearningsite.model.Lesson;
import ru.vsu.csf.corporatelearningsite.payload.*;
import ru.vsu.csf.corporatelearningsite.security.user.UserPrincipal;
import ru.vsu.csf.corporatelearningsite.services.CourseService;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping(CourseController.URL)
public class CourseController {

    public static final String URL = "/api/course";
    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/addListener")
    public ResponseEntity<?> addListener(@RequestBody AddListenerRequest request){
        courseService.addListener(request.getEmail(),request.getId());
        return ResponseEntity.ok(new ApiResponse(true, "User successfully added"));
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateCourseRequest request, Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        courseService.create(request, userPrincipal.getId());
        return ResponseEntity.ok(new ApiResponse(true, "Course created"));
    }

    @PutMapping("/{courseId}/mark/{userId}/{isPassed}")
    public ResponseEntity<?> setCourseMark(@PathVariable("courseId") Long courseId,
                                           @PathVariable("userId") UUID userId,
                                           @PathVariable("isPassed") boolean isPassed,
                                           Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        courseService.setMark(userPrincipal.getId(), courseId, userId, isPassed);
        return ResponseEntity.ok(new ApiResponse(true, "Mark set"));

    }

    @GetMapping("/{courseId}/myMark")
    public ResponseEntity<?> myMark(@PathVariable Long courseId, Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        boolean userMark = courseService.isUserPassed(courseId, userPrincipal.getId());
        return ResponseEntity.ok(userMark);
    }

}
