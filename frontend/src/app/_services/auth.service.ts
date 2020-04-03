import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import {HttpClient } from '@angular/common/http';
import { tap, shareReplay } from 'rxjs/operators';

import { User } from '../_models/user';
import * as jwt_decode from 'jwt-decode';
import { Role } from '../_models/role';

let _url: string = "http://localhost:8081/api";

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  public static TOKEN_KEY = 'token';
  public static CURRENT_USER_KEY = 'currentUser';
  public static LOGIN_URL = '/auth/login';
  public static SIGNUP_URL = '/auth/signup';
  
  private currentUserSubject: BehaviorSubject<User>;
  public currentUser: Observable<User>;

  constructor(private http: HttpClient) { 
    this.currentUserSubject = new BehaviorSubject<User>(JSON.parse(localStorage.getItem(AuthService.CURRENT_USER_KEY)));
    this.currentUser = this.currentUserSubject.asObservable();
  }

  public get currentUserValue(): User {
    return this.currentUserSubject.value;
  }

  login(email, password) {
    return this.http.post(_url + AuthService.LOGIN_URL, {
      "email": email,
      "password": password
    }).pipe(
      tap(res => this.setSession(res)),
      shareReplay()
    )
  }

  logout() {
    localStorage.removeItem(AuthService.CURRENT_USER_KEY)
    localStorage.removeItem(AuthService.TOKEN_KEY);
  }
  
  public isLoggedIn() {
    return !this.isTokenExpired();
  }
  
  public isLoggedOut() {
    return this.isTokenExpired();
  }

  private setSession(authResult) {
    const user = JSON.stringify(this.getUserFromToken(authResult.accessToken));
    localStorage.setItem(AuthService.CURRENT_USER_KEY, user);
    localStorage.setItem(AuthService.TOKEN_KEY, authResult.accessToken);
  }

  private isTokenExpired() {
    const token = localStorage.getItem(AuthService.TOKEN_KEY);
    if(!token) return true;

    const date = this.getTokenExpirationDate(token);
    if (date === undefined) return false;
    return !(date.valueOf() > new Date().valueOf());
  }

  private getTokenExpirationDate(token) {
    const decoded = JSON.parse(jwt_decode(token));

    if (decoded.exp === undefined) return null;

    const date = new Date(0);
    date.setUTCSeconds(decoded.exp);
    return date;
  }

  private getUserFromToken(token: string) {
    const decoded:any = jwt_decode(token);
    var authoritiesString:string = decoded.Authorities;
    var roles:Array<Role> = authoritiesString.split(",").map(str => Role[str]);
    var user = new User()
    user.roles = roles;
    return user;
  }
}
