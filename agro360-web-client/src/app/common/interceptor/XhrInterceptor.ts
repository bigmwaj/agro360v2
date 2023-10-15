import { HttpHandler, HttpHeaders, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { catchError } from "rxjs/operators";
import { environment } from "src/environments/environment";
import { UIService } from "../service/ui.service";

@Injectable({
    providedIn: 'root'
})
export class XhrInterceptor implements HttpInterceptor{
    constructor(
        private ui: UIService
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
        if( req.url.indexOf('credentials') < 0 ){
            headers = this.setJWT(headers);
        }
       
        headers = this.setLanguage(headers)
        headers = headers.append('X-Requested-With', 'XMLHttpRequest')
        return headers;
    }

    private setJWT(headers:HttpHeaders):HttpHeaders{
        /*const val = this.storageService.getJWT();
        if( null != val ){
            return headers.append('Authorization', val)
        }*/
        return headers;
    }

    private setLanguage(headers:HttpHeaders):HttpHeaders{
        return headers.set('Accept-Language', 'fr');
    }
}