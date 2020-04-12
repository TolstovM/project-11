import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

let _url = 'http://localhost:8081/api/homework';

@Injectable({
  providedIn: 'root'
})
export class HomeworkService {

  public static HOMEWORK_PROJECTION = "homeworkProjection";
  
  constructor(private http: HttpClient) { }

  public findByByLessonId(lessonId) {
    return this.http.get(_url + `/find/${lessonId}`);
  }
}
