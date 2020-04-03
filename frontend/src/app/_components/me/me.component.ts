import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/_services/user.service';

@Component({
  selector: 'app-me',
  templateUrl: './me.component.html',
  styleUrls: ['./me.component.css']
})
export class MeComponent implements OnInit {

  user;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getMe()
      .subscribe(data => this.user = data);
  }

}
