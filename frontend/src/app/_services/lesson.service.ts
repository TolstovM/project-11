import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Lesson} from "../_models/lesson";

let _url: string = "http://localhost:8081/api/lesson";

@Injectable({
  providedIn: 'root'
})
export class LessonService {

  public static LESSON_PROJECTION = "inlineUserWithRoles";
  public static LESSON_WITH_MATERIALS_PROJECTION = "lessonWithMaterialProjection";
  public static DOWNLOAD_URL_MATERIAL = 'http://localhost:8081/api/materials/downloadMaterial';
  public static DOWNLOAD_URL_HOMEWORK = 'http://localhost:8081/api/homework/downloadHomework';

  constructor(private http: HttpClient) {
  }

  public getLesson(name): any {
    return this.http.get(_url + `/name/${name}`);
  }

  public add(name, description, courseName) {
    return this.http.post<Lesson>(_url + `/courseName/${courseName}`, {"name": name, "description": description});
  }

  public getLessonMaterialsByName(name: string): any {
    return this.http.get(_url + `/materials/${name}`);
  }

  public getLessonMaterials(lesson: Lesson): any {
    return this.http.get(_url + `/materials/${lesson.name}`);
  }

  public findById(id, projection: string): any {
    return this.http.get(_url + `/${id}?projection=${projection}`);
  }
}
