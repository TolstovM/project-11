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

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ChangeProfileComponent } from './_components/change-profile/change-profile.component';
import { CourseListComponent } from './_components/course-list/course-list.component';
import { CourseAddFormComponent } from './_components/course-add-form/course-add-form.component';
import { CourseComponent } from './_components/course/course.component';
import { LessonAddFormComponent } from './_components/lesson-add-form/lesson-add-form.component';
import {CourseService} from "./_services/course.service";
import {ToastrModule} from "ngx-toastr";
import { UsersComponent } from './_components/users/users.component';
import { UserComponent } from './_components/user/user.component';
import { InviteComponent } from './_components/invite/invite.component';
import { AdminCoursesComponent } from './_components/admin-courses/admin-courses.component';
import { AdminCourseComponent } from './_components/admin-course/admin-course.component';
import { AdminCourseDetailsComponent } from './_components/admin-course-details/admin-course-details.component';
import { InstructorsListComponent } from './_components/instructors-list/instructors-list.component';
import { InstructorsSearchComponent } from './_components/instructors-search/instructors-search.component';
import { ListenersListComponent } from './_components/listeners-list/listeners-list.component';

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
    UsersComponent,
    AdminCoursesComponent,
    AdminCourseComponent,
    AdminCourseDetailsComponent,
    InstructorsListComponent,
    InstructorsSearchComponent,
    ListenersListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    ToastrModule.forRoot()
  ],
  providers: [
    AuthService,
    UserService,
    AuthGuard,
    CourseService,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
