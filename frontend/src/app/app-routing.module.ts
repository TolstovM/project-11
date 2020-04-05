import { NgModule, Component } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './_components/login/login.component';
import { MeComponent } from './_components/me/me.component';
import { Role } from './_models/role';
import { SignupComponent } from './_components/signup/signup.component';
import { UsersComponent } from './_components/users/users.component';


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
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
