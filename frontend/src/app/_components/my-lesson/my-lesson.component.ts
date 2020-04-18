import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import { LessonService } from 'src/app/_services/lesson.service';
import { HomeworkService } from 'src/app/_services/homework.service';
import { forkJoin } from 'rxjs';
import { FormBuilder, Validators } from '@angular/forms';
import { CommentService } from 'src/app/_services/comment.service';
import {Material} from "../../_models/material";
import {Lesson} from "../../_models/lesson";
import {ToastrService} from "ngx-toastr";
import {AuthService} from "../../_services/auth.service";

@Component({
  selector: 'app-my-lesson',
  templateUrl: './my-lesson.component.html',
  styleUrls: ['./my-lesson.component.css']
})
export class MyLessonComponent implements OnInit {

  id;
  lesson: Lesson = {} as Lesson;
  homework;
  form;
  materials: Material[];
  downloadUrl: string;
  fileToUpload: File = null;

  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private lessonService: LessonService,
    private homeworkService: HomeworkService,
    private fb: FormBuilder,
    private toastr: ToastrService,
    private commentService: CommentService,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.form = this.fb.group({
      text: ['', Validators.required]
    });
    this.id = this.route.snapshot.paramMap.get('lessonId');
    let llesson = this.lessonService.findById(this.id, LessonService.LESSON_WITH_MATERIALS_PROJECTION);
    let lhomework = this.homeworkService.getHomeworkById(this.id, this.authService.currentUserValue.uuid);
    forkJoin([llesson, lhomework])
    .subscribe(res => {
      this.lesson = res[0] as Lesson;
      this.homework = res[1];
      this.lessonService.getLessonMaterials(this.lesson).subscribe(data => {
        this.materials = data;
      });
    });
    this.downloadUrl = LessonService.DOWNLOAD_URL_HOMEWORK;
  }

  send() {
    var value = this.form.value;
    this.commentService.send(this.id, this.homework.id.userId, value.text)
      .subscribe(() => this.form.text.value = '');
  }

  reloadComponent() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.router.onSameUrlNavigation = 'reload';
    this.router.navigate(['/user/me/course/' + this.route.snapshot.paramMap.get('id') + '/lessons/' + this.lesson.id]);
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }

  uploadFileToActivity() {
    if(!this.fileToUpload){
      this.toastr.error("Файл не выбран");
    }
    else {
      this.homeworkService.postFile(this.fileToUpload, this.lesson.name).subscribe(data => {
        this.toastr.success("Вы успешно загрузили домашнюю работу");
        this.reloadComponent();
      }, error => {
        console.log(error);
      });
    }

  }

  onSubmit() {
    this.uploadFileToActivity();
  }
}
