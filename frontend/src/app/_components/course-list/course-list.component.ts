import { Component, OnInit } from '@angular/core';
import {Course} from "../../_models/course";
import {CourseService} from "../../_services/course.service";
import {LessonService} from "../../_services/lesson.service";

@Component({
  selector: 'app-course-list',
  templateUrl: './course-list.component.html',
  styleUrls: ['./course-list.component.css']
})
export class CourseListComponent implements OnInit {

  coursesWrapped: any;


  constructor(private courseService: CourseService) { }

  ngOnInit(): void {
    this.courseService.findAll().subscribe(data => {
      this.coursesWrapped = data;
      console.log(this.coursesWrapped);
    });

  }

}
