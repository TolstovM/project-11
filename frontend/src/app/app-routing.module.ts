import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './_components/login/login.component';
import { MeComponent } from './_components/me/me.component';
import { Role } from './_models/role';
import { SignupComponent } from './_components/signup/signup.component';
import {ChangeProfileComponent} from "./_components/change-profile/change-profile.component";

import {CourseListComponent} from "./_components/course-list/course-list.component";
import {CourseComponent} from "./_components/course/course.component";
import {CourseAddFormComponent} from "./_components/course-add-form/course-add-form.component";
import {LessonAddFormComponent} from "./_components/lesson-add-form/lesson-add-form.component";
import { UsersComponent } from './_components/users/users.component';
import {LessonComponent} from "./_components/lesson/lesson.component";
import { AdminCoursesComponent } from './_components/admin-courses/admin-courses.component';
import { AdminCourseDetailsComponent } from './_components/admin-course-details/admin-course-details.component';
import { MyCourseComponent } from './_components/my-course/my-course.component';
import { MyCoursesComponent } from './_components/my-courses/my-courses.component';
import { MyLessonComponent } from './_components/my-lesson/my-lesson.component';
import {HomeworkListComponent} from "./_components/homework-list/homework-list.component";
import {ConcreteHomeworkComponent} from "./_components/concrete-homework/concrete-homework.component";




const routes: Routes = [

  { path: 'auth/signup', component: SignupComponent },
  { path: 'auth/login', component: LoginComponent },
  {
    path: 'user',
    component: UsersComponent,
    data: { roles: [Role.ROLE_ADMIN] }
  },
  {
    path: 'user/me',
    component: MeComponent,
    data: { roles: [Role.ROLE_USER] }
  },
  {
    path: 'admin/course',
    component: AdminCoursesComponent,
    data: { roles: [Role.ROLE_ADMIN] }
  },
  {
    path: 'admin/course/details/:id',
    component: AdminCourseDetailsComponent,
    data: { roles: [Role.ROLE_ADMIN] }
  },
  {
    path: 'user/me/changeProfile',
    component: ChangeProfileComponent
  },
  { path: 'courses', component: CourseListComponent },
  { path: 'course/:name', component: CourseComponent },
  { path: 'add/course', component: CourseAddFormComponent },
  { path: 'add/lesson/:courseName', component: LessonAddFormComponent },
  {
      path: 'user/me/course',
      component: MyCoursesComponent,
      data: { roles: [Role.ROLE_USER] }
  },
  {
    path: 'user/me/course/:id',
    component: MyCourseComponent,
    data: { roles: [Role.ROLE_USER] }
  },
  {
    path: 'user/me/course/:id/lessons/:lessonId',
    component: MyLessonComponent,
    data: { roles: [Role.ROLE_USER] }
  },
  { path: 'lesson/homeworks',
    component: HomeworkListComponent,
    data: { roles: [Role.ROLE_INSTRUCTOR] }
  },
  { path: 'lesson/homework',
    component: ConcreteHomeworkComponent,
    data: { roles: [Role.ROLE_INSTRUCTOR] }
  },
  { path: 'lesson/:name',
    component: LessonComponent,
    data: { roles: [Role.ROLE_INSTRUCTOR] }
  }


];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule]
})
export class AppRoutingModule { }
