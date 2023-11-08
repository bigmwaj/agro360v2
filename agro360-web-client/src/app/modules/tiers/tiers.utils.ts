import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';
import { TiersBean } from 'src/app/backed/bean.tiers';
import { CommonUtlis } from 'src/app/common/utils/common.utils';

export class TiersUtils {

    static getTiersAsValueOptions(http: HttpClient, searchQuery: any): Observable<any> {
        const keyValMapper = (e: TiersBean) => `"${e.tiersCode.value}":"${e.tiersCode.value} ${e.tiersName.value}"`;
        return CommonUtlis.getBeansAsValueOptions(http, 'tiers/tiers', keyValMapper, searchQuery);
    }
}