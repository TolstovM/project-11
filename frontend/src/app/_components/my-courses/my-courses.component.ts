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
  page = 0;
  pages: number;
  size = 5;
  i: number;
  pagesIterable: number[] = [];

  constructor(
    private courseService: CourseService,
    private authService: AuthService
    ) { }

  ngOnInit(): void {
    this.currentUser = this.authService.currentUserValue;
    this.courseService.findAllForUser(this.currentUser.uuid, CourseService.COURSE_PROJECTION, this.page, this.size)
      .subscribe(data =>{
        this.coursesWrapped = data;
        console.log(this.coursesWrapped)
        this.pages = this.coursesWrapped.page.totalPages;
        this.pagesIterable = this.createRange(this.pages);
      } );




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
