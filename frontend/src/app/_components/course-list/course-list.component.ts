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
  page = 0;
  pages: number;
  size = 5;
  i: number;
  pagesIterable: number[] = [];

  constructor(private courseService: CourseService) { }

  ngOnInit(): void {
    this.courseService.findAll(this.page, this.size).subscribe(data => {
      this.coursesWrapped = data;
      this.pages = this.coursesWrapped.page.totalPages;
      this.pagesIterable = this.createRange(this.pages);
    });


  }

  createRange(number){
    var items: number[] = [];
    for(var i = 1; i <= number; i++){
      items.push(i);
    }
    return items;
  }

  setPage(i: number, event: any) {
    event.preventDefault();
    this.page = i;
    this.ngOnInit();
  }

  setPrevPage(event: any) {
    event.preventDefault();
    if(this.page > 0){
      this.page--;
      this.ngOnInit();
    }
  }

  setNextPage(event: any) {
    event.preventDefault();
    if(this.page < this.pages - 1){
      this.page++;
      this.ngOnInit();
    }
  }
}
