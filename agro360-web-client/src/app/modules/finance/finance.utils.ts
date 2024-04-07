import { HttpClient } from '@angular/common/http';

import { Observable, map } from 'rxjs';
import { RubriqueBean } from 'src/app/backed/bean.finance';
import { CommonUtlis } from 'src/app/common/utils/common.utils';

export class FinanceUtils {

    static rubriqueKeyValMapper = (e: RubriqueBean) => `"${e.rubriqueCode.value}":"${e.rubriqueCode.value} ${e.description.value}"`;

    static getRubriquesAsValueOptions(http: HttpClient, searchQuery: any): Observable<any> {
        return CommonUtlis.getBeansAsValueOptions(http, 'finance/rubrique', FinanceUtils.rubriqueKeyValMapper, searchQuery);
    }
}