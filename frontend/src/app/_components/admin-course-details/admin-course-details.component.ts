import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ActivatedRoute } from '@angular/router';
import { CourseService } from 'src/app/_services/course.service';

@Component({
  selector: 'app-admin-course-details',
  templateUrl: './admin-course-details.component.html',
  styleUrls: ['./admin-course-details.component.css']
})
export class AdminCourseDetailsComponent implements OnInit {

  id;
  course;

  constructor(private route: ActivatedRoute, private courseService:CourseService) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.courseService.getWithInstructorsAndListenersById(this.id)
      .subscribe(data => this.course = data);
  }

}
