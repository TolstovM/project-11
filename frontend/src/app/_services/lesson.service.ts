import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Course} from "../_models/course";

let _url: string = "http://localhost:8081/lesson";

@Injectable({
  providedIn: 'root'
})
export class LessonService {

  constructor(private http: HttpClient) {
  }

  public add(name, description, courseName) {
    return this.http.post<Course>(_url + `/${courseName}`, {"name": name, "description": description});
  }
}
