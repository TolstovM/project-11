import { Component, OnInit } from '@angular/core';
import {HomeworkService} from "../../_services/homework.service";
import {ActivatedRoute} from "@angular/router";
import {Subscription} from "rxjs";
import {UserService} from "../../_services/user.service";

@Component({
  selector: 'app-homework-list',
  templateUrl: './homework-list.component.html',
  styleUrls: ['./homework-list.component.css']
})
export class HomeworkListComponent implements OnInit {


  homeworks=[];

  private querySub: Subscription;
  lessonId: number
  constructor(private homeworkService: HomeworkService, private route: ActivatedRoute) {
    this.querySub = this.route.queryParams.subscribe(params => {
      this.lessonId = params['lessonId'];

    });
  }



  ngOnInit(): void {
    this.homeworkService.getHomeworkForLesson(this.lessonId).subscribe(
      data => {
        this.homeworks = data;
      }
    );
  }


}
