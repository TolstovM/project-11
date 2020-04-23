import { Component, OnInit } from '@angular/core';
import { Subscription, forkJoin } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { CourseService } from 'src/app/_services/course.service';
import { UserService } from 'src/app/_services/user.service';

@Component({
  selector: 'app-admin-course-details',
  templateUrl: './admin-course-details.component.html',
  styleUrls: ['./admin-course-details.component.css']
})
export class AdminCourseDetailsComponent implements OnInit {

  id;
  course;
  instructors;

  constructor(
    private route: ActivatedRoute, 
    private courseService:CourseService,
    private userService: UserService
    ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    let courseWithListeners = this.courseService.findById(this.id, CourseService.COURSE_WITH_LISTENERS_PROJECTION);
    let instructors = this.userService.findInstructorsByCourseId(this.id);

    forkJoin([courseWithListeners, instructors])
      .subscribe(res => {
        this.course = res[0];
        this.instructors = res[1];
      })
  }

}
