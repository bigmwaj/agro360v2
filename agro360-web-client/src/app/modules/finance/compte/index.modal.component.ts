
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTable } from '@angular/material/table';
import { map } from 'rxjs';
import { CompteBean, CompteSearchBean } from 'src/app/backed/bean.finance';
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
    selector: 'finance-compte-index-modal',
    templateUrl: './index.modal.component.html'
})
export class IndexModalComponent extends BeanList<CompteBean> implements OnInit {

    searchForm: CompteSearchBean;

    @ViewChild('comptesTable')
    table: MatTable<CompteBean>;

    displayedColumns: string[] = [
        'select',
        'compteCode',
        'description',
        'actions'
    ];

    constructor(
        private http: HttpClient,
        public dialog: MatDialog) {
        super()
    }

    override getViewChild(): MatTable<CompteBean> {
        return this.table;
    }
    
    getKeyLabel(bean: CompteBean): string {
        return bean.compteCode.value;
    }

    ngOnInit(): void {
        this.resetSearchFormAction()
    }

    resetSearchFormAction() {
        this.http
            .get("finance/compte/search-form")
            .subscribe(data => {
                this.searchForm = <CompteSearchBean>data;
                this.searchAction();
            });
    }

    searchAction() {
        let objJsonStr = JSON.stringify(this.searchForm);
        let objJsonB64 = btoa(objJsonStr);

        let queryParams = new HttpParams();
        queryParams = queryParams.append('q', objJsonB64);
        this.http
            .get("finance/compte", { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.setData(data.records);
            });
    }

    __add(queryParams: HttpParams) {
        this.http
            .get("finance/compte/create-form", { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.addItem(<CompteBean>data);
            });
    }

    addAction() {
        let queryParams = new HttpParams();
        this.__add(queryParams);
    }

    copyAction(bean: CompteBean) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('copyFrom', bean.compteCode.value);
        this.__add(queryParams);
    }

    deleteAction(bean: CompteBean) {
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
        this.http.post(`finance/compte`, BeanTools.reviewBeansAction(this.getData()))
            .subscribe(data => {
                console.log(data);
                this.dialog.closeAll();
            })
    }
}
