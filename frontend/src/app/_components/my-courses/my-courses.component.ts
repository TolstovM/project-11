import { Component, OnInit } from '@angular/core';
import { CourseService } from 'src/app/_services/course.service';
import { AuthService } from 'src/app/_services/auth.service';

@Component({
  selector: 'my-courses',
  templateUrl: './my-courses.component.html',
  styleUrls: ['./my-courses.component.css']
})
export class MyCoursesComponent implements OnInit {

  coursesWrapped;
  currentUser;
  constructor(
    private courseService: CourseService,
    private authService: AuthService
    ) { }

  ngOnInit(): void {
    this.currentUser = this.authService.currentUserValue;
    this.courseService.findAllForUser(this.currentUser.uuid, CourseService.COURSE_PROJECTION)
      .subscribe(data => this.coursesWrapped = data);
  }

}
