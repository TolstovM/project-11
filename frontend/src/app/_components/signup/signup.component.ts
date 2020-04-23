import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { AuthService } from 'src/app/_services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  form: FormGroup;

  constructor(private fb: FormBuilder,
    private authService: AuthService,
    private router: Router) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      inviteCode: ['', Validators.required],
      name: ['', Validators.required],
      email: ['', [Validators.required,Validators.email]],
      password: ['', Validators.required],
      confirmPassword: ['', Validators.required]
    });
  }

  signup() {
    const value = this.form.value;
    if (value.inviteCode && value.email && value.name && value.password && value.confirmPassword && value.password === value.confirmPassword) {
      this.authService.signup(value.inviteCode, value.email, value.name, value.password)
      .subscribe(
        () => this.router.navigateByUrl(AuthService.LOGIN_URL)
      )
    }
  }


}
