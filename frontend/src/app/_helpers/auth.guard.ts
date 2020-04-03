import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

import { AuthService } from '../_services/auth.service';

@Injectable({
    providedIn: 'root'
  })
export class AuthGuard implements CanActivate {

    constructor(private router: Router, private authService: AuthService) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const currentUser = this.authService.currentUserValue;
        
        if (!route.data.roles) {
            return true;
        }
        if (currentUser && this.hasAuthorities(currentUser, route)) {
            return true;
        }

        this.router.navigate([AuthService.LOGIN_URL], { queryParams: {returnUrl: state.url } });
        return false;
    }

    private hasAuthorities(user, route: ActivatedRouteSnapshot): boolean {
        var result = false;
        user.roles.forEach(role => {
            if (route.data.roles.indexOf(role) !== -1) {
                result = true;
            }
        });
        return result;
    }
    
}