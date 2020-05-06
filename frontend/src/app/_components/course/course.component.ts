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
  id: number;
  course: Course = {} as Course;
  lessons: Lesson[];
  private sub: Subscription;
  private qsub: Subscription;

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private route: ActivatedRoute,
    private courseService: CourseService,
    private toastr: ToastrService,
    private userService: UserService,

  ) { }

  ngOnInit(): void {
    this.qsub = this.route.queryParams.subscribe(params => {
      this.id = params['id'];
      this.loadCourse(this.id);
    });
    this.form = this.formBuilder.group({
      email: ['', Validators.required]
    })
  }

  get aControls() { return this.form.controls; }

  loadCourse(id: number) {
    this.courseService.findById(id, CourseService.COURSE_WITH_LESSONS_PROJECTION)
      .pipe(first())
      .subscribe( res => {
          this.course = res;
          this.lessons = this.course['lessons'];
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
  }

  onClick() {
    const value = this.form.value;
    this.userService.findUsersByEmailStartingWith(value.email, UserService.USER_WITH_ROLES_PROJECTION)
      .subscribe(data =>{
        this.users = data['_embedded']['users'];
      } )
  }
}
