import { HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError } from "rxjs/operators";
import { environment } from "src/environments/environment";
import { UIService } from "../service/ui.service";
import { AuthService } from "../service/auth.service";

@Injectable({
    providedIn: 'root'
})
export class XhrInterceptor implements HttpInterceptor{
    constructor(
        private ui: UIService,
        private authService: AuthService
    ){}

    intercept(req : HttpRequest<any>, next : HttpHandler){
        let xhr:any;
        xhr = req.clone({
            headers: this.setHeaders(req, req.headers),
            url:environment.backendUrl + '/' + req.url
        });
        return next.handle(xhr).pipe(catchError((err, caught) => this.ui.handleError(err, caught)));
    }

    private setHeaders(req : HttpRequest<any>, headers : HttpHeaders):HttpHeaders{
        if( !req.withCredentials ){
            headers = this.setAuthorizationHeader(headers);
        }
        headers = this.setLanguage(headers)
        headers = headers.append('X-Requested-With', 'XMLHttpRequest')
        return headers;
    }

    private setAuthorizationHeader(headers:HttpHeaders):HttpHeaders{
        headers = headers.set('Authorization', this.authService.credentials)
        headers = headers.set('X-XSRF-Token', this.authService.csrf)
        return headers;
    }

    private setLanguage(headers:HttpHeaders):HttpHeaders{
        return headers.set('Accept-Language', 'fr');
    }
}