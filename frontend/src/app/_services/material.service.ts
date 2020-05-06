import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Lesson} from "../_models/lesson";
import {map} from "rxjs/operators";
import {Observable} from "rxjs";

let _url: string = "http://localhost:8081/api/material";

@Injectable({
  providedIn: 'root'
})
export class MaterialService {

  constructor(private http: HttpClient) { }

  postFile(fileToUpload: File, lessonId) {
    const formData: FormData = new FormData();
    formData.append("material", fileToUpload);
    return this.http.post(_url + `/uploadMaterial/${lessonId}`, formData);
  }

  delete(id){
    return this.http.delete(_url + `/delete/${id}`);
  }

  public getLessonMaterials(lesson: Lesson): any {
    return this.http.get(_url + `/materials/${lesson.id}`);
  }

  public getLessonMaterialsById(id: number): any {
    return this.http.get(_url + `/materials/${id}`);
  }

  public download(name): Observable<any> {
    return this.http.get(_url + `/downloadMaterial/${name}`, { responseType: 'blob' }).pipe(
      map((res: Blob) => {
        return new Blob([res.slice()]);
      })
    );
  }
}
