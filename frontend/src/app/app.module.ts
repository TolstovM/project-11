import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './_components/login/login.component';
import { SignupComponent } from './_components/signup/signup.component';
import { MeComponent } from './_components/me/me.component';

import { AuthService } from './_services/auth.service';
import { UserService } from './_services/user.service';
import { AuthInterceptor } from './_helpers/auth.interceptor';
import { ErrorInterceptor } from './_helpers/error.interceptor';
import { AuthGuard } from './_helpers/auth.guard';
import { HomeworkService } from './_services/homework.service';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ChangeProfileComponent } from './_components/change-profile/change-profile.component';
import { CourseListComponent } from './_components/course-list/course-list.component';
import { CourseAddFormComponent } from './_components/course-add-form/course-add-form.component';
import { CourseComponent } from './_components/course/course.component';
import { LessonAddFormComponent } from './_components/lesson-add-form/lesson-add-form.component';
import {CourseService} from "./_services/course.service";
import { UsersComponent } from './_components/users/users.component';
import { UserComponent } from './_components/user/user.component';
import { InviteComponent } from './_components/invite/invite.component';
import {ToastrModule} from "ngx-toastr";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { LessonComponent } from './_components/lesson/lesson.component';
import { AdminCoursesComponent } from './_components/admin-courses/admin-courses.component';
import { AdminCourseComponent } from './_components/admin-course/admin-course.component';
import { AdminCourseDetailsComponent } from './_components/admin-course-details/admin-course-details.component';
import { InstructorsListComponent } from './_components/instructors-list/instructors-list.component';
import { InstructorsSearchComponent } from './_components/instructors-search/instructors-search.component';
import { ListenersListComponent } from './_components/listeners-list/listeners-list.component';
import { MyCoursesComponent } from './_components/my-courses/my-courses.component';
import { MyCourseComponent } from './_components/my-course/my-course.component';
import { CommentService } from './_services/comment.service';
import { from } from 'rxjs';
import { MyLessonComponent } from './_components/my-lesson/my-lesson.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    MeComponent,
    ChangeProfileComponent,
    CourseListComponent,
    CourseAddFormComponent,
    CourseComponent,
    LessonAddFormComponent,
    UsersComponent,
    UserComponent,
    InviteComponent,
    LessonComponent,
    UsersComponent,
    AdminCoursesComponent,
    AdminCourseComponent,
    AdminCourseDetailsComponent,
    InstructorsListComponent,
    InstructorsSearchComponent,
    ListenersListComponent,
    MyCoursesComponent,
    MyCourseComponent,
    LessonComponent,
    MyLessonComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot()
  ],
  providers: [
    AuthService,
    UserService,
    AuthGuard,
    CourseService,
    HomeworkService,
    CommentService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
