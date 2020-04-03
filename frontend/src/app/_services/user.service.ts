import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


let _url: string = "http://localhost:8081/api";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  public static ME_URL = '/user/me';

  constructor(private http: HttpClient) { }

  getMe() {
    return this.http.get(_url + UserService.ME_URL);
  }
}
