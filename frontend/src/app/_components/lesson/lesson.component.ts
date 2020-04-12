import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LessonService } from 'src/app/_services/lesson.service';
import { HomeworkService } from 'src/app/_services/homework.service';
import { forkJoin } from 'rxjs';
import { FormBuilder, Validators } from '@angular/forms';
import { CommentService } from 'src/app/_services/comment.service';

@Component({
  selector: 'app-lesson',
  templateUrl: './lesson.component.html',
  styleUrls: ['./lesson.component.css']
})
export class LessonComponent implements OnInit {
  
  id;
  lesson;
  homework;
  form;
  constructor(
    private route: ActivatedRoute,
    private lessonService: LessonService,
    private homeworkService: HomeworkService,
    private fb: FormBuilder,
    private commentService: CommentService
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      text: ['', Validators.required]
    });
    this.id = this.route.snapshot.paramMap.get('lessonId');
    let llesson = this.lessonService.findById(this.id, LessonService.LESSON_WITH_MATERIALS_PROJECTION);
    let lhomework = this.homeworkService.findByByLessonId(this.id);
    forkJoin([llesson, lhomework])
    .subscribe(res => {
      this.lesson = res[0];
      this.homework = res[1];
    });
  }

  send() {
    var value = this.form.value;
    this.commentService.send(this.id, this.homework.id.userId, value.text)
      .subscribe(() => this.form.text.value = '');
  }

}
