import { Component, OnInit } from '@angular/core';
import { CourseService } from 'src/app/_services/course.service';

@Component({
  selector: 'admin-courses',
  templateUrl: './admin-courses.component.html',
  styleUrls: ['./admin-courses.component.css']
})
export class AdminCoursesComponent implements OnInit {

  coursesWrapped;
  page = 0;
  pages: number;
  size = 4;
  pagesIterable: number[] = [];

  constructor(private courseService: CourseService) { }

  ngOnInit(): void {
    this.courseService.getAll(CourseService.COURSE_PROJECTION, this.page, this.size)
      .subscribe(data => {
        this.coursesWrapped = data;
        this.pages = this.coursesWrapped.page.totalPages;
        this.pagesIterable = this.createRange(this.pages);
      }
     );
  }

  createRange(number){
    var items: number[] = [];
    for(var i = 1; i <= number; i++){
      items.push(i);
    }
    return items;
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

  setPage(i: number, event: any) {
    event.preventDefault();
    this.page = i;
    this.ngOnInit();
  }
}
