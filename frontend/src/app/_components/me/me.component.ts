import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/_services/user.service';
import { AuthService } from 'src/app/_services/auth.service';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.css']
})
export class MeComponent implements OnInit {

  user;
  currentUser;

  constructor(private userService: UserService, private authService: AuthService) { }

  ngOnInit(): void {
    this.userService.getMe()
      .subscribe(data => this.user = data);
      this.currentUser = this.authService.currentUserValue;
      console.log(this.currentUser);
      console.log(this.currentUser["roles"]);
  }



}
