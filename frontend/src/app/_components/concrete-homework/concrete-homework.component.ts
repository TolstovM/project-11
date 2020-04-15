import { Component, OnInit } from '@angular/core';
import {HomeworkService} from "../../_services/homework.service";
import {ActivatedRoute} from "@angular/router";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-concrete-homework',
  templateUrl: './concrete-homework.component.html',
  styleUrls: ['./concrete-homework.component.css']
})
export class ConcreteHomeworkComponent implements OnInit {


  homework;
  lessonId;
  userId;

  private querySub: Subscription;

  constructor(private homeworkService: HomeworkService, private route: ActivatedRoute) {
    this.querySub = this.route.queryParams.subscribe(params => {
      this.lessonId = params['lessonId'];
      this.userId = params['userId'];
    });
  }



  ngOnInit(): void {
    this.homeworkService.getHomeworkById(this.lessonId, this.userId).subscribe(
      data => {
        this.homework = data;
        console.log(data);
        console.log(this.homework);
      }
    );
  }

}
