import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/_services/user.service';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  usersWrap: any;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getAll(UserService.USER_WITH_ROLES_PROJECTION)
      .subscribe(data => this.usersWrap = data);
  }

}
