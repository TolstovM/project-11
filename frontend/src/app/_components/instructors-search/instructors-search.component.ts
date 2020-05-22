import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/_services/user.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Role } from 'src/app/_models/role';
import { ActivatedRoute } from '@angular/router';
import { CourseService } from 'src/app/_services/course.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'instructors-search',
  templateUrl: './instructors-search.component.html',
  styleUrls: ['./instructors-search.component.css']
})
export class InstructorsSearchComponent implements OnInit {

  instructors;
  courseId;
  form: FormGroup;
  constructor(
    private userService: UserService,
    private courseService: CourseService,
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private toastr: ToastrService
    ) { }

  ngOnInit(): void {
    this.courseId = this.route.snapshot.paramMap.get('id');
    this.form = this.fb.group({
      email: ['', Validators.required]
    })
  }

  get f() { return this.form.controls; }

  onClick() {
    const value = this.form.value;
    this.userService.findUsersByEmailStartingWith(value.email, UserService.USER_WITH_ROLES_PROJECTION)
      .subscribe(data =>{
            this.instructors = data['_embedded'].users;
            console.log(this.instructors)
          console.log(this.instructors._embedded.users)
      }
          );
  }

  addInstructor(userId, email) {
    this.courseService.patchInstructor(this.courseId, userId)
      .subscribe(() => {
        this.toastr.success(`Пользователь ${email} был добавлен как инструктор на текущий курс.`);
        window.location.reload();
      });
  }

}
