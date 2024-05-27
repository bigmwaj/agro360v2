import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthService } from '../service/auth.service';

@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate {

    constructor(private authService: AuthService, private router: Router) {}

    canActivate( next: ActivatedRouteSnapshot, state : RouterStateSnapshot): boolean | UrlTree {
        const url: string = state.url;

        if( state.url == '/login' ){
            return true;
        }
        
        return this.checkLogin(url);
    }
    
    checkLogin(url: string) : boolean | UrlTree {


        if( this.authService.isLogged() ) {
            return true;
        }
        // Store the attempted URL for redirecting
        this.authService.redirectUrl = url;

        // Redirect to the login page
        return this.router.parseUrl("/login");
    }  
}