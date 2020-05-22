import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/_services/user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  usersWrap: any;
  page = 0;
  pages: number;
  size = 4;
  pagesIterable: number[] = [];

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getAll(UserService.USER_WITH_ROLES_PROJECTION, this.page, this.size)
      .subscribe(data => {
        this.usersWrap = data;
        this.pages = this.usersWrap.page.totalPages;
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
