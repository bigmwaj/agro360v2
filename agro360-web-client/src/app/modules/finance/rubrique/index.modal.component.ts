
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTable } from '@angular/material/table';
import { map } from 'rxjs';
import { RubriqueBean, RubriqueSearchBean } from 'src/app/backed/bean.finance';
import { EditActionEnumVd } from 'src/app/backed/vd.common';
import { BeanList } from 'src/app/common/component/bean.list';
import { BeanTools } from 'src/app/common/bean.tools';
import { SharedModule } from 'src/app/common/shared.module';
import { UIService } from 'src/app/common/service/ui.service';
import { MatDialog } from '@angular/material/dialog';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'finance-rubrique-index-modal',
    templateUrl: './index.modal.component.html'
})
export class IndexModalComponent extends BeanList<RubriqueBean> implements OnInit {

    searchForm: RubriqueSearchBean;

    @ViewChild('rubriquesTable')
    table: MatTable<RubriqueBean>;

    displayedColumns: string[] = [
        'select',
        'rubriqueCode',
        'type',
        'nom',
        'description',
        'actions'
    ];

    constructor(
        private http: HttpClient,
        public dialog: MatDialog) {
        super()
    }

    override getViewChild(): MatTable<RubriqueBean> {
        return this.table;
    }
    
    getKeyLabel(bean: RubriqueBean): string {
        return bean.rubriqueCode.value;
    }

    ngOnInit(): void {
        this.resetSearchFormAction()
    }

    resetSearchFormAction() {
        this.http
            .get("finance/rubrique/search-form")
            .subscribe(data => {
                this.searchForm = <RubriqueSearchBean>data;
                this.searchAction();
            });
    }

    searchAction() {
        let objJsonStr = JSON.stringify(this.searchForm);
        let objJsonB64 = btoa(objJsonStr);

        let queryParams = new HttpParams();
        queryParams = queryParams.append('q', objJsonB64);
        this.http
            .get("finance/rubrique", { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.setData(data.records);
            });
    }

    __add(queryParams: HttpParams) {
        this.http
            .get("finance/rubrique/create-form", { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.addItem(<RubriqueBean>data);
            });
    }

    addAction() {
        let queryParams = new HttpParams();
        this.__add(queryParams);
    }

    copyAction(bean: RubriqueBean) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('copyFrom', bean.rubriqueCode.value);
        this.__add(queryParams);
    }

    deleteAction(bean: RubriqueBean) {
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

    saveAction() {
        this.http.post(`finance/rubrique`, BeanTools.reviewBeansAction(this.getData())).
            subscribe(data => {
                console.log(data);                
                this.dialog.closeAll();
            })
    }
}
