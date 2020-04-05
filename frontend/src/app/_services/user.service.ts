import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


let _url: string = "http://localhost:8081/api";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  public static ME_URL = '/user/me';
  public static USERS_URL = '/user';
  public static UPDATE_ROLES_URL = '/user/update/authorities';
  public static INLINE = 'projection=inlineUserWithRoles'

  constructor(private http: HttpClient) { }

  getMe() {
    return this.http.get(_url + UserService.ME_URL);
  }

  getAll() {
    return this.http.get(_url + UserService.USERS_URL + '?' + UserService.INLINE);
  }

  updateRoles(userId: string, isAdmin: boolean, isInstructor: boolean) {
    return this.http.patch(_url + UserService.UPDATE_ROLES_URL, {
      "userId": userId,
      "isAdmin": isAdmin,
      "isInstructor": isInstructor
    })
  }
}
