import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


let _url = "http://localhost:8081/api";

@Injectable({
  providedIn: 'root'
})
export class GetService {

  constructor(private http: HttpClient) { }



  public get(uri) {
    return this.http.get(_url + uri);
  }
}
