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

    public static final String URL = "/api/сourse";
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
    public Optional<Course> getCourse(@PathVariable("name") String name){
        return courseService.get(name);
    }

    @GetMapping("lessons/{name}")
    public List<Lesson> getCourseLessons(@PathVariable("name") String name){
        return courseService.getLessons(name);
    }

    @PostMapping
    public ResponseEntity<Course> addCourse(@RequestBody AddCourseRequest request) {
        courseService.add(new Course(request.getName(), request.getDescription()));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/addListener")
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
