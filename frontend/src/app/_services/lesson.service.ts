import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Lesson} from "../_models/lesson";

let _base_url = window["baseUrl"];
let _url: string = `${_base_url}/api/lesson`;

@Injectable({
  providedIn: 'root'
})
export class LessonService {

  public static LESSON_PROJECTION = "inlineUserWithRoles";
  public static LESSON_WITH_MATERIALS_PROJECTION = "lessonWithMaterialProjection";

  constructor(private http: HttpClient) {
  }

  public getLesson(id: number): any {
    return this.http.get(_url + `/name/${id}`);
  }

  public add(name, description, courseId: number) {
    return this.http.post<Lesson>(_url + `/courseId/${courseId}`, {"name": name, "description": description});
  }

  public findById(id, projection: string): any {
    return this.http.get(_url + `/${id}?projection=${projection}`);
  }

  public deleteLesson(id: number) {
    return this.http.delete(_url + `/delete/${id}`);
  }
}
