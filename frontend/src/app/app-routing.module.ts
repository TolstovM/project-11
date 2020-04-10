import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './_components/login/login.component';
import { MeComponent } from './_components/me/me.component';
import { Role } from './_models/role';
import { SignupComponent } from './_components/signup/signup.component';
import {ChangeProfileComponent} from "./_components/change-profile/change-profile.component";
import {AppComponent} from "./app.component";


const routes: Routes = [

  { path: 'auth/signup', component: SignupComponent },
  { path: 'auth/login', component: LoginComponent },
  {
    path: 'user/me',
    component: MeComponent,
    data: { roles: [Role.ROLE_USER] }
  },
  {
    path: 'user/me/changeProfile',
    component: ChangeProfileComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
