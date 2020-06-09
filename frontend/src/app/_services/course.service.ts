import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';
import {Course} from "../_models/course";

let _base_url = window["baseUrl"];
let _url: string = `${_base_url}/api/course`;

@Injectable({
  providedIn: 'root'
})
export class CourseService {

  public static COURSE_PROJECTION = "courseProjection";
  public static COURSE_WITH_LESSONS_PROJECTION = "courseWithLessonsProjection";
  public static COURSE_WITH_LISTENERS_PROJECTION = "courseWithListenersProjection";

  constructor(private http: HttpClient) {
  }

  public findAll(page: number, size: number): Observable<Course[]> {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get<Course[]>(_url, {params});
  }

  public getCourse(name): any {
    return this.http.get(_url + `/${name}`);
  }

  public add(name, description) {
    return this.http.post<Course>(_url + `/create`, {"name": name, "description": description });
  }

  public getCourseLessons(name): any {
    return this.http.get(_url + `/lessons/${name}`);
  }

  public addListener(email: any, id: string) {
    return this.http.post(_url+'/addListener',{"email":email,"id":id});
  }

  public getAll(projection: string, page: number, size: number) {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get(_url + `?projection=${projection}`, {params});
  }

  public patchInstructor(courseId, userId) {
    const headers = new HttpHeaders({'Content-Type': 'text/uri-list'});
    return this.http.patch(_url + `/${courseId}/instructors`,
    _url + `/user/${userId}`, { headers });
  }

  setMark(courseId: number, userId: any, mark: boolean) {
    return this.http.put(_url + `/${courseId}/mark/${userId}/${mark}`, null);
  }

  public deleteInstructor(courseId, userId) {
    return this.http.delete(_url + `/${courseId}/instructors/${userId}`);
  }

  public findAllForUser(userId, projection, page: number, size: number) {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get(_url + `/search/findAllByUserId?uuid=${userId}&projection=${projection}`, {params});
  }

  public findById(courseId, projection: string):any {
    return this.http.get(_url + `/${courseId}?projection=${projection}`);
  }

  public findAllByInstructorId(uuid: string, page: number, size: number) {
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());
    return this.http.get(_url + `/search/findAllByInstructorId?uuid=${uuid}`, {params});
  }

  public isPassed(courseId: number) {
    return this.http.get(_url + `/${courseId}/myMark`);
  }
}
