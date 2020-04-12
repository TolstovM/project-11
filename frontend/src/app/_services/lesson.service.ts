import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Lesson} from "../_models/lesson";

let _url: string = "http://localhost:8081/lesson";

@Injectable({
  providedIn: 'root'
})
export class LessonService {

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
}
