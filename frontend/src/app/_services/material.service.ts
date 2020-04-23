import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Lesson} from "../_models/lesson";

let _base_url = window["baseUrl"]; 
let _url: string = `${_base_url}/api/material`;

@Injectable({
  providedIn: 'root'
})
export class MaterialService {

  constructor(private http: HttpClient) { }

  postFile(fileToUpload: File, lessonName) {
    const formData: FormData = new FormData();
    formData.append("material", fileToUpload);
    return this.http.post(_url + `/uploadMaterial/${lessonName}`, formData);
  }

  delete(id){
    return this.http.delete(_url + `/delete/${id}`);
  }

  public getLessonMaterials(lesson: Lesson): any {
    return this.http.get(_url + `/materials/${lesson.name}`);
  }

  public getLessonMaterialsByName(name: string): any {
    return this.http.get(_url + `/materials/${name}`);
  }
}
