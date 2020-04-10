import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'admin-course',
  templateUrl: './admin-course.component.html',
  styleUrls: ['./admin-course.component.css']
})
export class AdminCourseComponent implements OnInit {

  @Input() course;
  href;

  constructor() { }

  ngOnInit(): void {
    this.href = `/admin/course/details/${this.course.id}`;
  }

}
