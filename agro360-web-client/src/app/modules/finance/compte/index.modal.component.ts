
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTable } from '@angular/material/table';
import { map } from 'rxjs';
import { CompteBean, CompteSearchBean } from 'src/app/backed/bean.finance';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';
import { BeanList } from 'src/app/modules/common/bean.list';
import { BeanTools } from 'src/app/modules/common/bean.tools';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { MatDialog } from '@angular/material/dialog';
import { Message } from 'src/app/backed/message';

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

    displayedColumns: string[] = [
        'select',
        'compteCode',
        'description',
        'actions'
    ];

    constructor(
        private http: HttpClient,
        public dialog: MatDialog,
        private ui: UIService) {
        super()
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
        if( bean.action == ClientOperationEnumVd.CREATE ){
            this.removeItem(bean);
        }else {
            if( bean.action != ClientOperationEnumVd.DELETE){
                bean.action = ClientOperationEnumVd.DELETE;
                bean.valueChanged = true;
            }else{                
                bean.action = ClientOperationEnumVd.SYNC;
                bean.valueChanged = false;
            }
        }
    }

    saveAction() {
        this.http.post(`finance/compte`, BeanTools.reviewBeansAction(this.getData()))
        .pipe(map((e: any) => <any>e))
        .subscribe(data => {
            this.dialog.closeAll();
            this.ui.displayFlashMessage(<Array<Message>>data.messages);
        })
    }
}
