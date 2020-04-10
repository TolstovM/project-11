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

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignupComponent,
    MeComponent,
    ChangeProfileComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule
  ],
  providers: [
    AuthService,
    UserService,
    AuthGuard,
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
