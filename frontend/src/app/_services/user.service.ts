import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";


let _url: string = "http://localhost:8081/api";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  public static ME_URL = '/user/me';
  static CHANGE_URL = '/user/me/change';


  constructor(private http: HttpClient) {

  }



  getMe() {
    return this.http.get(_url + UserService.ME_URL);
  }

    changeProfile(email,name):Observable<any> {
        return this.http.post<any>(_url + UserService.CHANGE_URL,{
          'email':email,
          'name':name,

      })
    }

  changePassword(password):Observable<any> {
    return this.http.post<any>(_url + UserService.CHANGE_URL,{
      'password':password
    })
  }
}
