import { Component, OnInit, Input } from '@angular/core';
import { Role } from 'src/app/_models/role';
import { FormGroup, FormBuilder } from '@angular/forms';
import { UserService } from 'src/app/_services/user.service';

@Component({
  selector: 'user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  isAdmin: boolean;
  isInstructor: boolean;

  @Input() user: any;
  form: FormGroup;

  constructor(private fb: FormBuilder, private userService: UserService) { }

  ngOnInit(): void {
    this.isAdmin = this.user.roles && this.user.roles.map(role => role.name).indexOf(Role.ROLE_ADMIN) !== -1;
    this.isInstructor = this.user.roles && this.user.roles.map(role => role.name).indexOf(Role.ROLE_INSTRUCTOR) !== -1;
  }

  onClick() {
    this.userService.updateRoles(this.user.id, this.isAdmin, this.isInstructor)
    .subscribe(res => console.log(res));
  }

}
