import { HttpClient, HttpParams } from '@angular/common/http';
import { AbstractBean } from '../../backed/bean.common';
import { Observable, map } from 'rxjs';


export class CommonUtlis {
    static BASE_URL = "http://localhost:8080";

    static encodeQuery(searchQuery: any): HttpParams {
        let objJsonStr = JSON.stringify(searchQuery);
        let objJsonB64 = btoa(objJsonStr);
        let queryParams = new HttpParams();
        return queryParams.append('q', objJsonB64);
    }

    static getBeansAsValueOptions<B extends AbstractBean>(http: HttpClient, url: string, keyValMapper: (e: B) => string, searchQuery: any): Observable<any> {
        return http.get(CommonUtlis.BASE_URL + url, { params: CommonUtlis.encodeQuery(searchQuery) })
            .pipe(map((e: any) => e.records.map(keyValMapper)))
            .pipe(map((e: any) => e.join(",")))
            .pipe(map((e: any) => JSON.parse(`{${e}}`)));
    }
}
