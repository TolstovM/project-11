import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from "rxjs";

let _base_url = window["baseUrl"]; 
let _url: string = `${_base_url}/api`;

@Injectable({
  providedIn: 'root'
})
export class UserService {

  public static ME_URL = '/user/me';
  public static CHANGE_URL = '/user/me/change';
  public static USERS_URL = '/user';
  public static GET_USER_URL = '/user/getById';
  public static UPDATE_ROLES_URL = '/user/update/authorities';
  public static INLINE = 'projection=inlineUserWithRoles';
  public static INVITE_PATH = '/user/invite';
  public static FIND_BY_EMAIL_STARTING_WITH = `/user/search/findAllByEmailStartingWith?email=`;

  public static USER_PROJECTION = "userProjection";
  public static USER_WITH_ROLES_PROJECTION = "inlineUserWithRoles";

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

  getAll(projection: string) {
    return this.http.get(_url + UserService.USERS_URL + `?projection=${projection}`);
  }

  updateRoles(userId: string, isAdmin: boolean, isInstructor: boolean) {
    return this.http.patch(_url + UserService.UPDATE_ROLES_URL, {
      "userId": userId,
      "isAdmin": isAdmin,
      "isInstructor": isInstructor
    })
  }

  getInviteCode() {
    return this.http.get(_url + UserService.INVITE_PATH);
  }

  findUsersByEmailStartingWith(email: string, projection: string) {
    return this.http.get(_url + `/user/search/findAllByEmailStartingWith?email=${email}&projection=${projection}`);
  }

  findInstructorsByCourseId(courseId) {
    return this.http.get(_url + `/user/search/findInstructorsByCourseId?courseId=${courseId}&projection=${UserService.USER_WITH_ROLES_PROJECTION}`);
  }

  getUserNameById(userId: string):Observable<any> {
    return this.http.get<any>(_url+UserService.GET_USER_URL+`?userId=${userId}`);
  }
}
