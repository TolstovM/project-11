import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {CourseService} from "../../_services/course.service";
import {first} from "rxjs/operators";
import {LessonService} from "../../_services/lesson.service";
import {Subscription} from "rxjs";

@Component({
  selector: 'app-lesson-add-form',
  templateUrl: './lesson-add-form.component.html',
  styleUrls: ['./lesson-add-form.component.css']
})
export class LessonAddFormComponent implements OnInit {

  addForm: FormGroup;
  private sub: Subscription;
  courseName: string;
  id:number;
  private qsub: Subscription

  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private toastr: ToastrService,
    private courseService: CourseService,
    private lessonService: LessonService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.addForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required]
    });
    this.sub = this.route.params.subscribe(params => {
      this.courseName = params['courseName'];
    });
    this.qsub = this.route.queryParams.subscribe(params => {
      this.id = params['id'];

    });
  }

  get f() { return this.addForm.controls; }

  onSubmit() {
    if (this.addForm.invalid) {
      return;
    }

    this.lessonService.add(this.f.name.value, this.f.description.value, this.courseName)
      .pipe(first())
      .subscribe(
        () => {
          this.toastr.success("Вы успешно добавили урок");
          setTimeout(() => {
            this.router.navigate(['/course',this.courseName], {queryParams: {id: this.id}});
          }, 1000);
        });
  }
}
