import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AbstractBean } from '../../../backed/bean.common';
import { Observable, map } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class CommonService {

    encodeQuery(searchQuery: any): HttpParams {
        let objJsonStr = JSON.stringify(searchQuery);
        let objJsonB64 = btoa(objJsonStr);
        let queryParams = new HttpParams();
        return queryParams.append('q', objJsonB64);
    }

    getBeansAsValueOptions<B extends AbstractBean>(http: HttpClient, url: string, keyValMapper: (e: B) => string, searchQuery: any): Observable<any> {
        return http.get( url, { params: this.encodeQuery(searchQuery) })
            .pipe(map((e: any) => e.records.map(keyValMapper)))
            .pipe(map((e: any) => e.join(",")))
            .pipe(map((e: any) => JSON.parse(`{${e}}`)));
    }

    getBeansAsValueOptions2<B extends AbstractBean>(http: HttpClient, url: string, keyValMapper: (e: B) => string, searchQuery: any): Observable<any> {
        return http.get( url, { params: searchQuery })
            .pipe(map((e: any) => e.records.map(keyValMapper)))
            .pipe(map((e: any) => e.join(",")))
            .pipe(map((e: any) => JSON.parse(`{${e}}`)));
    }
}
