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
  success;
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
    let message = '';
    if (value.email && value.name) {
      this.userService.changeProfile(value.email, value.name)
        .subscribe(
          data => message = data['message']
        )
      this.success = 'Успешная смена данных профиля'
      setTimeout(() => {

        this.router.navigate(['/user/me'],{queryParams:{"message":message}})

      }, 2000);
    }

  }

  confirmPass() {
    const value = this.form.value;
    let message = '';
    if (value.password && value.confirmPassword && value.password === value.confirmPassword) {
      this.userService.changePassword(value.password)
        .subscribe(
          data => message = data['message']
        )
      this.success = 'Успешная смена пароля';
      setTimeout(() => {

        this.router.navigate(['/user/me'],{queryParams:{"message":message}})

      }, 2000);
    }
  }
}
