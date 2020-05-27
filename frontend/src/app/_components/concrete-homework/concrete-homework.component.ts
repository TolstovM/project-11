import { Component, OnInit } from '@angular/core';
import {HomeworkService} from "../../_services/homework.service";
import {ActivatedRoute, Router} from "@angular/router";
import {Subscription} from "rxjs";
import {LessonService} from "../../_services/lesson.service";
import {ToastrService} from "ngx-toastr";
import {FormBuilder, Validators} from "@angular/forms";
import {CommentService} from "../../_services/comment.service";



@Component({
  selector: 'app-concrete-homework',
  templateUrl: './concrete-homework.component.html',
  styleUrls: ['./concrete-homework.component.css']
})
export class ConcreteHomeworkComponent implements OnInit {


  homework;
  lessonId;
  userId;
  downloadUrl: string;
  private querySub: Subscription;
  result: any;
  form
  resultForm;
  public text = {
    editorData: ''
  };

  constructor(private homeworkService: HomeworkService, private route: ActivatedRoute,
                private toastr: ToastrService, private router:Router, private fb: FormBuilder, private commentService: CommentService) {
    this.querySub = this.route.queryParams.subscribe(params => {
      this.lessonId = params['lessonId'];
      this.userId = params['userId'];
    });


  }



  ngOnInit(): void {
    this.form = this.fb.group({
      text: ['', Validators.required],

    });
    this.resultForm = this.fb.group({
      result: ['', Validators.required],
    });
    this.homeworkService.getHomeworkById(this.lessonId, this.userId).subscribe(
      data => {
        this.homework = data;
        console.log(this.homework);
      }
    );
    this.downloadUrl = LessonService.DOWNLOAD_URL_HOMEWORK;
  }


  send() {
    var value = this.resultForm.value;
    if(value.result!==undefined){
      this.homeworkService.saveHomeworkResult(value.result, this.lessonId, this.userId).subscribe(
        ()=>{
          this.toastr.success("Сохранено");
          setTimeout(() => {

            this.router.navigate(['/lesson/homeworks'],{queryParams:{lessonId:this.lessonId}});

          }, 2000);
        }
      )
    }
    else{
      this.toastr.warning("Вы не выбрали отметку о зачете");
    }


  }

  sendComment() {
    var value = this.form.value;
    this.commentService.send(this.lessonId, this.homework.id.userId, value.text)
      .subscribe(() => {
        this.toastr.success("Комментарий сохранен");
        this.form.text.value = ''
      })
      ;
    setTimeout(() => {
      this.ngOnInit();
    }, 2000);
  }
}
