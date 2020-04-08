import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/_services/user.service';

@Component({
  selector: 'invite',
  templateUrl: './invite.component.html',
  styleUrls: ['./invite.component.css']
})
export class InviteComponent implements OnInit {

  inviteCode;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  onClick() {
    this.userService.getInviteCode()
    .subscribe(data => this.inviteCode = data);
  }

}
