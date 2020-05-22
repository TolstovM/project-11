import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {first} from "rxjs/operators";
import {CourseService} from "../../_services/course.service";
import * as ClassicEditor from '@ckeditor/ckeditor5-build-classic';

@Component({
  selector: 'app-course-add-form',
  templateUrl: './course-add-form.component.html',
  styleUrls: ['./course-add-form.component.css']
})
export class CourseAddFormComponent implements OnInit {

  addForm: FormGroup;
  error;
  public Editor = ClassicEditor;
  constructor(
    private formBuilder: FormBuilder,
    private router: Router,
    private toastr: ToastrService,
    private courseService: CourseService
  ) { }

  public text = {
    editorData: ''
  };

  ngOnInit() {
    this.addForm = this.formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  get f() { return this.addForm.controls; }

  onSubmit() {
    if (this.addForm.invalid) {
      this.error="Заполните все поля"
      return;
    }
    else {
      this.courseService.add(this.f.name.value, this.f.description.value)
        .pipe(first())
        .subscribe(
          () => {
            this.toastr.success("Вы успешно добавили курс");
            this.router.navigate(['courses'])
          });
    }

  }

}
