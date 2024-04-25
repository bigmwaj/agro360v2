import { HttpClient } from '@angular/common/http';

import { Observable } from 'rxjs';
import { PartnerBean } from 'src/app/backed/bean.core';
import { CommonUtlis } from 'src/app/modules/common/utils/common.utils';

export class CoreUtils {

    static getPartnerAsValueOptions(http: HttpClient, searchQuery: any): Observable<any> {
        const keyValMapper = (e: PartnerBean) => `"${e.partnerCode.value}":"${e.partnerCode.value} ${e.partnerName.value}"`;
        return CommonUtlis.getBeansAsValueOptions(http, 'core/partner', keyValMapper, searchQuery);
    }
}