import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AuthService} from "../../_services/auth.service";
import {CourseService} from "../../_services/course.service";
import {Subscription} from "rxjs";
import {first} from "rxjs/operators";
import {Course} from "../../_models/course";
import {Lesson} from "../../_models/lesson";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit {

  addListenerForm: FormGroup;

  name: string;
  course: Course = {} as Course;
  lessons: Lesson[];
  private sub: Subscription;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private courseService: CourseService,
    private toastr: ToastrService
  ) { }

  ngOnInit(): void {

    this.sub = this.route.params.subscribe(params => {
      this.name = params['name'];
      this.loadCourse(this.name);
    });
    this.addListenerForm = this.formBuilder.group({
        email:['', Validators.required]
    }
    );
  }

  get aControls() { return this.addListenerForm.controls; }

  loadCourse(name: string) {
    this.courseService.getCourse(name)
      .pipe(first())
      .subscribe( res => {
          this.course = res;
        },
        error => {
          console.log(error);
        });

    this.courseService.getCourseLessons(name)
      .pipe(first())
      .subscribe( res => {
          this.lessons = res;
        },
        error => {
          console.log(error);
        });
  }

  addListener() {
    if(this.addListenerForm.invalid){
      return;
    }
    else{
      this.courseService.addListener(this.aControls.email.value, this.course.id).pipe(first())
        .subscribe(
          () => {
            this.toastr.success("Вы успешно добавили слушателя на курс");
          });


    }

  }
}
