package ru.vsu.csf.corporatelearningsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.corporatelearningsite.exceptions.ResourceNotFoundException;
import ru.vsu.csf.corporatelearningsite.model.Course;
import ru.vsu.csf.corporatelearningsite.model.Lesson;
import ru.vsu.csf.corporatelearningsite.model.User;
import ru.vsu.csf.corporatelearningsite.payload.AddListenerRequest;
import ru.vsu.csf.corporatelearningsite.payload.ApiResponse;
import ru.vsu.csf.corporatelearningsite.security.CurrentUser;
import ru.vsu.csf.corporatelearningsite.security.user.UserPrincipal;
import ru.vsu.csf.corporatelearningsite.services.CourseService;
import ru.vsu.csf.corporatelearningsite.services.CustomUserDetailsService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public Iterable<Course> getCourses(){
        return courseService.findAll();
    }

    @GetMapping("/{name}")
    public Optional<Course> getCourses(@PathVariable("name") String name){
        return courseService.get(name);
    }

    @GetMapping("lessons/{name}")
    public List<Lesson> getCourseLessons(@PathVariable("name") String name){
        return courseService.getLessons(name);
    }

    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        courseService.add(course);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<?> addListener(@RequestBody AddListenerRequest request){
        courseService.addListener(request.getEmail(),request.getId());
        return ResponseEntity.ok(new ApiResponse(true, "User successfully added"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Course> deleteCourse(@PathVariable("id") Long id) {
        courseService.delete(id);
        return ResponseEntity.ok().build();
    }






}
