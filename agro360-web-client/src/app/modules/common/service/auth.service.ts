import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { SessionStorageService } from '../store/session-storage.service';

@Injectable({
  providedIn: 'root'
})

export class AuthService {
    
    // store the URL so we can redirect after logging in
    redirectUrl: string | null = null;
    
    get credentials() :string{
        const cred = this.sessionService.token;
        if( cred ){
            return cred;
        }
        throw Error("Credential non défini!");
    }
   
    get csrf():string{
        const cred = this.sessionService.csrf;
        if( cred ){
            return cred;
        }
        throw Error("Csrf non défini!");
    }
    
    constructor(
        private http:HttpClient,
        private router:Router,
        private sessionService: SessionStorageService
    ){}
    
    login(credentials : {username:string, password:string}, callback: null | (() => any) = null) : Subscription {
        const auth = 'Basic ' + btoa(credentials.username + ':' + credentials.password);

        return this.http.get("", {
            observe:'response', 
            withCredentials: true, 
            headers:{'Authorization': auth}
        }).subscribe((data : any) => {
            this.sessionService.token = auth;
            this.sessionService.tokenExpirationDate = 30_000_000;
            this.sessionService.userInfos = data.body.record;
            const csrfToken = document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1');
            this.sessionService.csrf = csrfToken;

            if( null == callback ){
                if( this.redirectUrl ){
                    this.router.navigateByUrl(this.redirectUrl)
                    this.redirectUrl = null;
                }else{
                    this.router.navigateByUrl("/home")
                }
            }else{
                callback()
            }
        });
    }

    logout() {
        this.http.post("logout", {
           // withCredentials: true, 
        }).subscribe();
        
        this.sessionService.clear();
        this.redirectToLoginPage()
    }  

    redirectToLoginPage(){        
        this.router.navigateByUrl("/login");
    }

    isLogged():boolean{
        return this.sessionService.isSessionAlive()
    }

    getFullname(){
        return ''
    }
}
