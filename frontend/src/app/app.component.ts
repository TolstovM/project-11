import {Component, OnInit} from '@angular/core';
import { User } from './_models/user';
import { Router } from '@angular/router';
import { AuthService } from './_services/auth.service';
import { Role } from './_models/role';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Сайт корпоративного обучения';
  currentUser: User;



  constructor(private router: Router, private authSevice: AuthService) {

  }

  ngOnInit(): void {
    this.authSevice.currentUser
      .subscribe(x => this.currentUser = x);
  }

  get isAdmin(): boolean {
    return this.currentUser && this.currentUser.roles.indexOf(Role.ROLE_ADMIN) !== -1;
  }

  get isLogged(): boolean {
    return this.currentUser !== null;
  }

  logout() {
    this.authSevice.logout();
    this.router.navigate([AuthService.LOGIN_URL]);
  }

  refresh(){
    setTimeout(() => {

      window.location.reload();
    }, 100);

  }
}
