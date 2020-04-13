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
import {UserService} from "../../_services/user.service";
import {User} from "../../_models/user";

@Component({
  selector: 'app-course',
  templateUrl: './course.component.html',
  styleUrls: ['./course.component.css']
})
export class CourseComponent implements OnInit {

  form: FormGroup;

  users: User[];
  name: string;
  course: Course = {} as Course;
  lessons: Lesson[];
  private sub: Subscription;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private courseService: CourseService,
    private toastr: ToastrService,
    private userService: UserService
  ) { }

  ngOnInit(): void {

    this.sub = this.route.params.subscribe(params => {
      this.name = params['name'];
      this.loadCourse(this.name);
    });
    this.form = this.formBuilder.group({
      email: ['', Validators.required]
    })
  }

  get aControls() { return this.form.controls; }

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

  addListener(email: string) {
      this.courseService.addListener(email, this.course.id).pipe(first())
        .subscribe(
          () => {
            this.toastr.success("Вы успешно добавили слушателя на курс");
          });


    // }

  }

  onClick() {
    const value = this.form.value;
    this.userService.findUsersByEmailStartingWith(value.email)
      .subscribe(data =>{
        this.users = data['_embedded']['users'];
      } )
  }
}
