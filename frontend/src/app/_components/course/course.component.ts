import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../_services/auth.service";
import {CourseService} from "../../_services/course.service";
import {Subscription} from "rxjs";
import {first} from "rxjs/operators";
import {Course} from "../../_models/course";
import {Lesson} from "../../_models/lesson";

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit {

  name: string;
  course: Course = {} as Course;
  lessons: Lesson[];
  private sub: Subscription;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private courseService: CourseService
  ) { }

  ngOnInit(): void {
    this.sub = this.route.params.subscribe(params => {
      this.name = params['name'];
      this.loadCourse(this.name);
    });
  }

  loadCourse(name: string) {
    this.courseService.getCourse(name)
      .pipe(first())
      .subscribe( res => {
          this.course = res;
        },
        error => {
          console.log(error);
        });

    this.courseService.getCourseLessons(name)
      .pipe(first())
      .subscribe( res => {
          this.lessons = res;
        },
        error => {
          console.log(error);
        });
  }
}
