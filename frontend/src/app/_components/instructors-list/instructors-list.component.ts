import { Component, OnInit, Input } from '@angular/core';
import { UserService } from 'src/app/_services/user.service';
import { CourseService } from 'src/app/_services/course.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Route } from '@angular/compiler/src/core';
import { ToastrService } from 'ngx-toastr';
import { Role } from 'src/app/_models/role';

@Component({
  selector: 'instructors-list',
  templateUrl: './instructors-list.component.html',
  styleUrls: ['./instructors-list.component.css']
})
export class InstructorsListComponent implements OnInit {

  courseId;
  @Input() instructors;
  constructor(
    private courseService: CourseService,
    private route: ActivatedRoute,
    private toastr: ToastrService
    ) { }

  ngOnInit(): void {
    this.courseId = this.route.snapshot.paramMap.get('id');
  }

  deleteInstructor(userId, email) {
    this.courseService.deleteInstructor(this.courseId, userId)
      .subscribe(() => {
        this.toastr.success(`Инструктор ${email} удален с курса`);
        window.location.reload();
      })
  }

  isInstructor(user) {
    return user.roles && user.roles.map(role => role.name).indexOf(Role.ROLE_INSTRUCTOR) !== -1;
  }
}
