import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AbstractBean } from '../../backed/bean.common';
import { Observable, map } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { FlashMessageComponent } from '../flash-message.component';
import { Message } from 'src/app/backed/message';

@Injectable({
    providedIn: 'root'
})
export class CommonService {

    constructor(public _snackBar: MatSnackBar){
        
    }

    readonly BASE_URL = "http://localhost:8080";

    getBackendUrl(url: string): string {
        return `${this.BASE_URL}/${url}`;
    }

    encodeQuery(searchQuery: any): HttpParams {
        let objJsonStr = JSON.stringify(searchQuery);
        let objJsonB64 = btoa(objJsonStr);
        let queryParams = new HttpParams();
        return queryParams.append('q', objJsonB64);
    }

    getBeansAsValueOptions<B extends AbstractBean>(http: HttpClient, url: string, keyValMapper: (e: B) => string, searchQuery: any): Observable<any> {
        return http.get(this.BASE_URL + url, { params: this.encodeQuery(searchQuery) })
            .pipe(map((e: any) => e.records.map(keyValMapper)))
            .pipe(map((e: any) => e.join(",")))
            .pipe(map((e: any) => JSON.parse(`{${e}}`)));
    }

    displayFlashMessage(messages: Array<Message>){
        this._snackBar.openFromComponent(FlashMessageComponent, {
            duration: 5 * 1000,
            data: messages
        });
    }
}
