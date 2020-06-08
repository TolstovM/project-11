import { Component, OnInit } from '@angular/core';
import { CourseService } from 'src/app/_services/course.service';
import { ActivatedRoute } from '@angular/router';
import { GetService } from 'src/app/_services/get-service.service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'my-course',
  templateUrl: './my-course.component.html',
  styleUrls: ['./my-course.component.css']
})
export class MyCourseComponent implements OnInit {

  course;
  lessonsWrapped;
  id;
  isPassed;
  constructor(
    private courseService: CourseService,
    private route: ActivatedRoute,
    private getService: GetService
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    let course = this.courseService.findById(this.id, CourseService.COURSE_WITH_LESSONS_PROJECTION);
    let isPassed = this.courseService.isPassed(this.id)
    forkJoin([course, isPassed])
    .subscribe(res => {
      this.course = res[0];
      this.isPassed = res[1];
    })
  }

}
