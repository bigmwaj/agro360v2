import { HttpClient } from '@angular/common/http';

import { Observable, map } from 'rxjs';
import { ArticleBean, MagasinBean, UniteBean } from 'src/app/backed/bean.stock';
import { CommonUtlis } from 'src/app/common/utils/common.utils';

export class StockUtils {

    static uniteKeyValMapper = (e: UniteBean) => `"${e.uniteCode.value}":"${e.uniteCode.value} ${e.description.value}"`;

    static getArticlesAsValueOptions(http: HttpClient, searchQuery: any): Observable<any> {
        const keyValMapper = (e: ArticleBean) => `"${e.articleCode.value}":"${e.articleCode.value} ${e.description.value}"`;
        return CommonUtlis.getBeansAsValueOptions(http, '/stock/article', keyValMapper, searchQuery);
    }

    static getMagasinsAsValueOptions(http: HttpClient, searchQuery: any): Observable<any> {
        const keyValMapper = (e: MagasinBean) => `"${e.magasinCode.value}":"${e.magasinCode.value} ${e.description.value}"`;
        return CommonUtlis.getBeansAsValueOptions(http, '/stock/magasin', keyValMapper, searchQuery);
    }

    static getUnitesAsValueOptions(http: HttpClient, searchQuery: any): Observable<any> {
        return CommonUtlis.getBeansAsValueOptions(http, '/stock/unite', StockUtils.uniteKeyValMapper, searchQuery);
    }
}