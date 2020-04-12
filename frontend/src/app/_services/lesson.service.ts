import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Course} from "../_models/course";

let _url: string = "http://localhost:8081/lesson";
let _url_api: string = "http://localhost:8081/api/lesson";

@Injectable({
  providedIn: 'root'
})
export class LessonService {

  public static LESSON_PROJECTION = "inlineUserWithRoles";
  public static LESSON_WITH_MATERIALS_PROJECTION = "lessonWithMaterialProjection";

  constructor(private http: HttpClient) {
  }

  public add(name, description, courseName) {
    return this.http.post<Course>(_url + `/${courseName}`, {"name": name, "description": description});
  }

  public findById(id, projection: string) {
    return this.http.get(_url_api + `/${id}?projection=${projection}`);
  }
}
