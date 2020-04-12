import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Lesson} from "../_models/lesson";

let _url: string = "http://localhost:8081/lesson";
let _url_api: string = "http://localhost:8081/api/lesson";

@Injectable({
  providedIn: 'root'
})
export class LessonService {

  public static LESSON_PROJECTION = "inlineUserWithRoles";
  public static LESSON_WITH_MATERIALS_PROJECTION = "lessonWithMaterialProjection";
  public static DOWNLOAD_URL_MATERIAL = 'http://localhost:8081/materials/downloadMaterial';

  constructor(private http: HttpClient) {
  }

  public getLesson(name): any {
    return this.http.get(_url + `/${name}`);
  }

  public add(name, description, courseName) {
    return this.http.post<Lesson>(_url + `/${courseName}`, {"name": name, "description": description});
  }

  public getLessonMaterials(name): any {
    return this.http.get(_url + `/materials/${name}`);
  }

  public findById(id, projection: string) {
    return this.http.get(_url_api + `/${id}?projection=${projection}`);
  }
}
