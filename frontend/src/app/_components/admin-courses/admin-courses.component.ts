import { Component, OnInit } from '@angular/core';
import { CourseService } from 'src/app/_services/course.service';

@Component({
  selector: 'admin-courses',
  templateUrl: './admin-courses.component.html',
  styleUrls: ['./admin-courses.component.css']
})
export class AdminCoursesComponent implements OnInit {

  coursesWrapped;

  constructor(private courseService: CourseService) { }

  ngOnInit(): void {
    this.courseService.getAll(CourseService.COURSE_PROJECTION)
      .subscribe(data => this.coursesWrapped = data);
  }

}
