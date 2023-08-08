import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AppConstants } from '../../core/app-constants';
import { AuthService } from '../../core/services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate( next: ActivatedRouteSnapshot, state : RouterStateSnapshot): boolean | UrlTree {
    const url: string = state.url;
    return this.checkLogin(url);
  }
  
  checkLogin(url: string) : boolean | UrlTree {
    if( this.authService.isLogged() ) {
      return true;
    }

    console.log('Guard running redirection to login page from ' + url)
    // Store the attempted URL for redirecting
    this.authService.redirectUrl = url;

    // Redirect to the login page
    return this.router.parseUrl(AppConstants.LOGIN_PAGE_URL);
  }  
}