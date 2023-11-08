import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ArticleBean, MagasinBean, UniteBean } from 'src/app/backed/bean.stock';
import { CommonService } from 'src/app/common/service/common.service';

@Injectable({
    providedIn:'root'
})
export class StockService extends CommonService {

    readonly uniteKeyValMapper = (e: UniteBean) => `"${e.uniteCode.value}":"${e.uniteCode.value} ${e.description.value}"`;

    readonly magasinKeyValMapper = (e: MagasinBean) => `"${e.magasinCode.value}":"${e.magasinCode.value} ${e.description.value}"`;

    readonly articleKeyValMapper = (e: ArticleBean) => `"${e.articleCode.value}":"${e.articleCode.value} ${e.description.value}"`;

    getArticlesAsValueOptions(http: HttpClient, searchQuery: any): Observable<any> {
        return this.getBeansAsValueOptions(http, 'stock/article', this.articleKeyValMapper, searchQuery);
    }

    getMagasinsAsValueOptions(http: HttpClient, searchQuery: any): Observable<any> {
        return this.getBeansAsValueOptions(http, 'stock/magasin', this.magasinKeyValMapper, searchQuery);
    }

    getUnitesAsValueOptions(http: HttpClient, searchQuery: any): Observable<any> {
        return this.getBeansAsValueOptions(http, 'stock/unite', this.uniteKeyValMapper, searchQuery);
    }
}