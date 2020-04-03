import { Component, OnInit, Input } from '@angular/core';
import { Role } from 'src/app/_models/role';
import { FormGroup, FormBuilder } from '@angular/forms';

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

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.isAdmin = this.user.roles && this.user.roles.map(role => role.name).indexOf(Role.ROLE_ADMIN) !== -1;
    this.isInstructor = this.user.roles && this.user.roles.map(role => role.name).indexOf(Role.ROLE_INSTRUCTOR) !== -1;
  }

  onClick() {
    console.log(this.isAdmin);
  }

}
