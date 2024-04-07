import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CompteBean, RubriqueBean } from 'src/app/backed/bean.finance';
import { CommonService } from 'src/app/common/service/common.service';

@Injectable({
    providedIn:'root'
})
export class FinanceService extends CommonService {

    readonly rubriqueKeyValMapper = (e: RubriqueBean) => `"${e.rubriqueCode.value}":"${e.rubriqueCode.value} ${e.description.value}"`;

    readonly compteKeyValMapper = (e:CompteBean) => `"${e.compteCode.value}":"${e.compteCode.value} ${e.description.value}"`;

    getRubriquesAsValueOptions(http: HttpClient, searchQuery: any): Observable<any> {
        return this.getBeansAsValueOptions(http, 'finance/rubrique', this.rubriqueKeyValMapper, searchQuery);
    }

    getComptesAsValueOptions(http: HttpClient, searchQuery: any): Observable<any> {
        return this.getBeansAsValueOptions(http, 'finance/compte', this.compteKeyValMapper, searchQuery);
    }
}