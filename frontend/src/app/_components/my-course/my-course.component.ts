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
  constructor(
    private courseService: CourseService,
    private route: ActivatedRoute,
    private getService: GetService
  ) { }

  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    let course = this.courseService.findById(this.id);
    let lessons = this.getService.get(`/course/${this.id}/lessons`);

    forkJoin([course, lessons]).subscribe( res => {
      this.course = res[0];
      this.lessonsWrapped = res[1];
    })
    
  }

}
