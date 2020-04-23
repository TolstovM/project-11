import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {Observable} from "rxjs";

let _base_url = window["baseUrl"]; 
let _url = `${_base_url}/api/homework`;

@Injectable({
  providedIn: 'root'
})
export class HomeworkService {

  public static HOMEWORK_PROJECTION = "homeworkProjection";

  constructor(private http: HttpClient) { }

  public findByLessonId(lessonId) {
    return this.http.get(_url + `/find/${lessonId}`);
  }

  public getHomeworkForLesson(lessonId: number):Observable<any> {
    return this.http.get<any>(_url + `/findAll/${lessonId}`);
  }

  getHomeworkById(lessonId: number, userId):Observable<any> {
    const params = new HttpParams()
      .set('lessonId',lessonId.toString())
      .set('userId',userId.toString());

    return this.http.get<any>(_url + `/findHomework`,{params})
  }


  postFile(fileToUpload: File, lessonName) {
    const formData: FormData = new FormData();
    formData.append("homework", fileToUpload);
    return this.http.post(_url + `/uploadHomework/${lessonName}`, formData);
  }
  
  saveHomeworkResult(result: any, lessonId:number, userId:any) {
    return this.http.post(_url+`/checkHomework`,{result:result, lessonId:lessonId, userId:userId});
  }
}
