import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { SessionStorageService } from '../store/session-storage.service';

@Injectable({
  providedIn: 'root'
})

export class AuthService {
    
    // store the URL so we can redirect after logging in
    redirectUrl: string | null = null;
    
    get credentials():string{
        const cred = this.sessionService.token;
        if( cred ){
            return cred;
        }
        throw Error("Credential non défini");
    }

    
    get csrf():string{
        const cred = this.sessionService.csrf;
        if( cred ){
            return cred;
        }
        throw Error("Csrf non défini");
    }
    
    constructor(
        private http:HttpClient,
        private router:Router,
        private sessionService: SessionStorageService
    ){}
    
    login(credentials : {username:string, password:string}) : Observable<boolean> | boolean {
        const auth = 'Basic ' + btoa(credentials.username + ':' + credentials.password)
        this.http.get("", {
            observe:'response', 
            withCredentials: true, 
            headers:{'Authorization': auth}
        }).subscribe((data : any) => {
            this.sessionService.token = auth;
            this.sessionService.tokenExpirationDate = 30_000_000;
            this.sessionService.userInfos = data.body.record;
            const csrfToken = document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1');
            this.sessionService.csrf = csrfToken;

            if( this.redirectUrl ){
                this.router.navigateByUrl(this.redirectUrl)
                this.redirectUrl = null;
            }else{
                this.router.navigateByUrl("/home")
            }
        });
        return true;
    }

    logout() {
        this.http.post("logout", {
           // withCredentials: true, 
        }).subscribe(() => {});
        
        this.sessionService.clear();
        this.router.navigateByUrl("/login");
    }  

    isLogged():boolean{
        return this.sessionService.isSessionAlive()
    }

    getFullname(){
        return ''
    }
}
