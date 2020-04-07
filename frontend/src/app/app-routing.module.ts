import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './_components/login/login.component';
import { MeComponent } from './_components/me/me.component';
import { Role } from './_models/role';
import { SignupComponent } from './_components/signup/signup.component';
import {CourseListComponent} from "./_components/course-list/course-list.component";
import {CourseComponent} from "./_components/course/course.component";
import {CourseAddFormComponent} from "./_components/course-add-form/course-add-form.component";
import {LessonAddFormComponent} from "./_components/lesson-add-form/lesson-add-form.component";


const routes: Routes = [
  { path: 'auth/signup', component: SignupComponent },
  { path: 'auth/login', component: LoginComponent },
  {
    path: 'user/me',
    component: MeComponent,
    data: { roles: [Role.ROLE_USER] }
  },
  { path: 'courses', component: CourseListComponent },
  { path: 'course/:name', component: CourseComponent },
  { path: 'add/course', component: CourseAddFormComponent },
  { path: 'add/lesson/:courseName', component: LessonAddFormComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
