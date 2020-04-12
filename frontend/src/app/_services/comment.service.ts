import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { from } from 'rxjs';
let _url = "http://localhost:8081/api/comment";

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient) { }

  public send(lessonId, homeworkOwnerId, text) {
    return this.http.post(_url + `/send`, {
      lessonId: lessonId,
      homeworkOwnerId: homeworkOwnerId,
      text: text
    });
  }
}
