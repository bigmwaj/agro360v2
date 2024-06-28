import { HttpEvent, HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError, concatAll, map } from "rxjs/operators";
import { environment } from "src/environments/environment";
import { UIService } from "../service/ui.service";
import { AuthService } from "../service/auth.service";
import { MatDialog } from "@angular/material/dialog";
import { LoginDialogComponent } from "../../home/index/login.dialog.component";
import { EMPTY, Observable, of } from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class XhrInterceptor implements HttpInterceptor{
    constructor(
        private dialog: MatDialog,
        private ui: UIService,
        private authService: AuthService
    ){}

    intercept(req : HttpRequest<any>, next : HttpHandler): Observable<HttpEvent<any>>{      
        try {
            return of(req)
                .pipe(map(e => this.fixBackendUrl(e)))
                .pipe(map(e => this.prepareRequestHeader(e)), concatAll())
                .pipe(map(e => next.handle(e)), concatAll())
                .pipe(catchError((err, caught) => this.ui.handleError(err, caught)));
        } catch (error) {
            this.authService.redirectToLoginPage()
            return EMPTY;
        }  
    }

    private fixBackendUrl(req : HttpRequest<any>) : HttpRequest<any>{
        return req.clone({url:`${environment.backendUrl}/${req.url}`})
    }
    
    private prepareRequestHeader(req : HttpRequest<any>) : Observable<HttpRequest<any>>{
        const headers = this.setGlobalHeaderAttributes(req.headers);
       
        if( !req.withCredentials ){
            return this.setAuthorizationHeader(req, headers);
        }

        return of(req.clone({headers: headers}));
    }

    private setAuthorizationHeader(req : HttpRequest<any>, headers:HttpHeaders):Observable<HttpRequest<any>>{
        if( !this.authService.isLogged() ){            
            return this.displayAuthDialog().pipe(map((e) => {
                if( e == true ){
                    headers = this.setAuthHeaderAttributes(headers);
                }else if( e == false ){                    
                    throw new Error("Cancel the request!")
                }else if( e == undefined ){
                    // Keep dialog open
                }
                return req.clone({headers: headers});
            }));
        }else{
            headers = this.setAuthHeaderAttributes(headers);
            return of(req.clone({headers: headers}));
        }
    }
    
    private setAuthHeaderAttributes(headers:HttpHeaders) : HttpHeaders{
        headers = headers.set('Authorization', this.authService.credentials);
        headers = headers.set('X-XSRF-Token', this.authService.csrf);

        return headers;
    }

    private setGlobalHeaderAttributes(headers:HttpHeaders) : HttpHeaders{
        headers = headers.set('Accept-Language', 'fr');
        headers = headers.append('X-Requested-With', 'XMLHttpRequest');

        return headers;
    }

    private displayAuthDialog() {
        return this.dialog.open(LoginDialogComponent, {
            disableClose: true
        }).afterClosed();
    }
}