import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AuthService } from '../_services/auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    public static AUTH_TOKEN_KEY = "Authorization";

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        const token = localStorage.getItem(AuthService.TOKEN_KEY);

        

        if (token) {
            const cloned = req.clone({
                headers: req.headers.set(AuthInterceptor.AUTH_TOKEN_KEY, token)
            });
            return next.handle(cloned);
        } else {
            return next.handle(req);
        }
    }
}
