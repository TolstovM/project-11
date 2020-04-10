import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AuthService } from 'src/app/_services/auth.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription} from "rxjs";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  form: FormGroup;

  message: string;
  querySubscription: Subscription;

  constructor(private fb: FormBuilder,
    private authService: AuthService,
    private router: Router, private route: ActivatedRoute) {
    this.querySubscription = route.queryParams.subscribe(
      (queryParam: any) => {

        this.message = queryParam['message'];


      }
    );
  }

  ngOnInit(): void {
    this.form = this.fb.group({
      email: ['', [Validators.required,Validators.email]],
      password: ['', Validators.required],
    });
  }

  login() {
    const value = this.form.value;
    if (value.email && value.password) {
      this.authService.login(value.email, value.password)
          .subscribe(
            () => {
              this.router.navigateByUrl('/');
            }
          );
    }
  }

}
