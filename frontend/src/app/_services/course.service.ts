import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Course} from "../_models/course";

let _url: string = "http://localhost:8081/course";
let _url_api: string = "http://localhost:8081/api/course";

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  public static COURSE_PROJECTION = "courseProjection";
  public static COURSE_WITH_LESSONS_PROJECTION = "courseWithLessonsProjection";
  public static COURSE_WITH_LISTENERS_PROJECTION = "courseWithListenersProjection";

  constructor(private http: HttpClient) {
  }

  public findAll(): Observable<Course[]> {
    return this.http.get<Course[]>(_url);
  }

  public getCourse(name): any {
    return this.http.get(_url + `/${name}`);
  }

  public add(name, description) {
    return this.http.post<Course>(_url, {"name": name, "description": description});
  }

  public getCourseLessons(name): any {
    return this.http.get(_url + `/lessons/${name}`);
  }

  public addListener(email: any, id: string) {
    return this.http.post(_url+'/addListener',{"email":email,"id":id});
  }

  public getAll(projection: string) {
    return this.http.get(_url_api + `?projection=${projection}`);
  }

  public patchInstructor(courseId, userId) {
    const headers = new HttpHeaders({'Content-Type': 'text/uri-list'});
    return this.http.patch(_url_api + `/${courseId}/instructors`,
    _url_api + `/user/${userId}`, { headers });
  }

  public deleteInstructor(courseId, userId) {
    return this.http.delete(_url_api + `/${courseId}/instructors/${userId}`);
  }

  public findAllForUser(userId, projection) {
    return this.http.get(_url_api + `/search/findAllByUserId?uuid=${userId}&projection=${projection}`);
  }

  public findById(courseId, projection: string):any {
    return this.http.get(_url_api + `/${courseId}?projection=${projection}`);
  }

}
