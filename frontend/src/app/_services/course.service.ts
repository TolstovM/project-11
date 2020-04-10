import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Course} from "../_models/course";

let _url: string = "http://localhost:8081/course";

@Injectable({
  providedIn: 'root'
})
export class CourseService {

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

  addListener(email: any, id: string) {
    return this.http.post(_url+'/addListener',{"email":email,"id":id});
  }
}
