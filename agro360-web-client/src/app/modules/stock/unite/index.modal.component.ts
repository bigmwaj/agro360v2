
import { CommonModule } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatTable, MatTableModule } from '@angular/material/table';
import { map } from 'rxjs';
import { UniteBean, UniteSearchBean } from 'src/app/backed/bean.stock';
import { EditActionEnumVd } from 'src/app/backed/vd.common';
import { BeanList } from 'src/app/common/bean.list';
import { BeanTools } from 'src/app/common/bean.tools';
import { SharedModule } from 'src/app/common/shared.module';

const BASE_URL = "http://localhost:8080";

@Component({
    standalone: true,
    imports: [
        CommonModule,
        MatButtonModule,
        MatIconModule,
        MatDialogModule,
        MatCheckboxModule,
        MatTableModule,
        SharedModule
    ],
    selector: 'stock-unite-index-modal',
    templateUrl: './index.modal.component.html'
})
export class IndexModalComponent extends BeanList<UniteBean> implements OnInit {

    searchForm: UniteSearchBean;

    @ViewChild('unitesTable')
    table: MatTable<UniteBean>;

    displayedColumns: string[] = [
        'select',
        'uniteCode',
        'description',
        'actions'
    ];

    constructor(private http: HttpClient) {
        super()
    }

    override getViewChild(): MatTable<UniteBean> {
        return this.table;
    }
    
    getKeyLabel(bean: UniteBean): string {
        return bean.uniteCode.value;
    }

    ngOnInit(): void {
        this.resetSearchFormAction()
    }

    resetSearchFormAction() {
        this.http
            .get(BASE_URL + "/stock/unite/search-form")
            .subscribe(data => {
                this.searchForm = <UniteSearchBean>data;
                this.searchAction();
            });
    }

    searchAction() {
        let objJsonStr = JSON.stringify(this.searchForm);
        let objJsonB64 = btoa(objJsonStr);

        let queryParams = new HttpParams();
        queryParams = queryParams.append('q', objJsonB64);
        this.http
            .get(BASE_URL + "/stock/unite", { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.setData(data.records);
            });
    }

    __add(queryParams: HttpParams) {
        this.http
            .get(BASE_URL + "/stock/unite/create-form", { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.addItem(<UniteBean>data);
            });
    }

    addAction() {
        let queryParams = new HttpParams();
        this.__add(queryParams);
    }

    copyAction(bean: UniteBean) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('copyFrom', bean.uniteCode.value);
        this.__add(queryParams);
    }

    deleteAction(bean: UniteBean) {
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
            //this.updatePrixTotalEvent(ligne)
        }
    }

    saveAction() {
        this.http.post(BASE_URL + `/stock/unite`, BeanTools.reviewBeansAction(this.getData())).subscribe(data => console.log(data))
    }
}
