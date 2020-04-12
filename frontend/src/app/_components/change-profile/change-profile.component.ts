import { Component, OnInit } from '@angular/core';
import {UserService} from "../../_services/user.service";
import {AuthService} from "../../_services/auth.service";
import {User} from "../../_models/user";
import {Observable, Subscription} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-change-profile',
  templateUrl: './change-profile.component.html',
  styleUrls: ['./change-profile.component.css']
})
export class ChangeProfileComponent implements OnInit {

  name: string;
  email: string;
  password: string;
  form: FormGroup;
  private querySubscription:Subscription;

  constructor(private fb: FormBuilder,
              private userService: UserService,
              private router: Router,
              private route:ActivatedRoute) {
    this.querySubscription = route.queryParams.subscribe(
      (queryParam: any) => {
        this.name = queryParam['name'];
        this.email = queryParam['email'];
        this.password = queryParam['password'];


      }
    );
  }

  ngOnInit(): void {

    if(!this.password){
      this.form = this.fb.group({
        name: [this.name, Validators.required],
        email: [this.email, [Validators.required,Validators.email]],
      });
    }
    else{
      this.form = this.fb.group({
        password: [this.name, Validators.required],
        confirmPassword: [this.email, Validators.required],
      });
    }


  }

  confirm(){
    const value = this.form.value;
    console.log(value.email);
    console.log(value.name);
    if (value.email && value.name) {
      this.userService.changeProfile(value.email, value.name)
        .subscribe(
          data => this.router.navigate([AuthService.LOGIN_URL],{queryParams:{"message":data['message']}})
        )
    }

  }

  confirmPass() {
    const value = this.form.value;
    if (value.password && value.confirmPassword && value.password === value.confirmPassword) {
      this.userService.changePassword(value.password)
        .subscribe(
          data => this.router.navigate([AuthService.LOGIN_URL],{queryParams:{"message":data['message']}})
        )
    }
  }
}
