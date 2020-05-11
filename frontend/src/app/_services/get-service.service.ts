import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

let _base_url = window["baseUrl"]; 
let _url = `${_base_url}/api`;

@Injectable({
  providedIn: 'root'
})
export class GetService {

  constructor(private http: HttpClient) { }



  public get(uri) {
    return this.http.get(_url + uri);
  }
}
