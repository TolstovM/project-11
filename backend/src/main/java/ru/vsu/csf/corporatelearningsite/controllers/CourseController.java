package ru.vsu.csf.corporatelearningsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.corporatelearningsite.model.Course;
import ru.vsu.csf.corporatelearningsite.model.Lesson;
import ru.vsu.csf.corporatelearningsite.payload.AddCourseRequest;
import ru.vsu.csf.corporatelearningsite.payload.AddListenerRequest;
import ru.vsu.csf.corporatelearningsite.payload.ApiResponse;
import ru.vsu.csf.corporatelearningsite.services.CourseService;

import java.util.List;
import java.util.Optional;


//todo: delete getCourses, getCourses(String), getCourseLessons, addCourse, deleteCourse
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

}
