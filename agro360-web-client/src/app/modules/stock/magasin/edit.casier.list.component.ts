import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatTable } from '@angular/material/table';
import { map } from 'rxjs';
import { CasierBean } from 'src/app/backed/bean.stock';
import { EditActionEnumVd } from 'src/app/backed/vd.common';
import { BeanList } from 'src/app/common/component/bean.list';
import { SharedModule } from 'src/app/common/shared.module';

const BASE_URL = "http://localhost:8080";

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'stock-magasin-edit-casier-list',
    templateUrl: './edit.casier.list.component.html'
})
export class EditCasierListComponent extends BeanList<CasierBean> implements OnInit {

    getKeyLabel(bean: CasierBean): string {
        return bean.casierCode.value;
    }

    @Input()
    magasinCode: string;

    @Input()
    casiers: Array<CasierBean>;

    @ViewChild(MatTable)
    table: MatTable<CasierBean>;

    displayedColumns: string[] = [
        'select',
        'casierCode',
        'description',
        'actions'
    ];

    constructor(private http: HttpClient) {
        super()
    }

    override getViewChild(): MatTable<CasierBean> {
        return this.table;
    }

    ngOnInit(): void {
        this.setData(this.casiers)
    }

    private __add(queryParams: HttpParams) {
        this.http
            .get(BASE_URL + "/stock/magasin/casier/create-form", { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.addItem(<CasierBean>data);
            });
    }

    addAction() {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('magasinCode', this.magasinCode);

        this.__add(queryParams);
    }

    copyAction(casier: CasierBean) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('magasinCode', this.magasinCode);
        queryParams = queryParams.append('copyFrom', casier.casierCode.value);

        this.__add(queryParams);
    }

    deleteAction(bean: CasierBean) {
        if( bean.action == EditActionEnumVd.CREATE ){
            this.removeItem(bean);
        }else {
            if( bean.action != EditActionEnumVd.DELETE){
                bean.action = EditActionEnumVd.DELETE;
                bean.valueChanged = true;
            }else{                
                bean.action = EditActionEnumVd.SYNC;
                bean.valueChanged = false;
            }
        }
    }
}
