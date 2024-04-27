
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTable } from '@angular/material/table';
import { map } from 'rxjs';
import { TaxeBean } from 'src/app/backed/bean.finance';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';
import { BeanList } from 'src/app/modules/common/bean.list';
import { BeanTools } from 'src/app/modules/common/bean.tools';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { MatDialog } from '@angular/material/dialog';
import { Message } from 'src/app/backed/message';
import { UIService } from 'src/app/modules/common/service/ui.service';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'finance-taxe-index-modal',
    templateUrl: './index.modal.component.html'
})
export class IndexModalComponent extends BeanList<TaxeBean> implements OnInit {

    displayedColumns: string[] = [
        'select',
        'taxeCode',
        'taux',
        'description',
        'actions'
    ];

    constructor(
        private http: HttpClient,
        public dialog: MatDialog,
        private ui: UIService) {
        super()
    }
    
    getKeyLabel(bean: TaxeBean): string {
        return bean.taxeCode.value;
    }

    ngOnInit(): void {
        this.resetSearchFormAction()
    }

    resetSearchFormAction() {
        this.searchAction();
    }

    searchAction() {
        this.http
            .get("finance/taxe")
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.setData(data.records);
            });
    }

    __add(queryParams: HttpParams) {
        this.http
            .get("finance/taxe/create-form", { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.addItem(<TaxeBean>data);
            });
    }

    addAction() {
        let queryParams = new HttpParams();
        this.__add(queryParams);
    }

    copyAction(bean: TaxeBean) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('copyFrom', bean.taxeCode.value);
        this.__add(queryParams);
    }

    deleteAction(bean: TaxeBean) {
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
        this.http.post(`finance/taxe`, BeanTools.reviewBeansAction(this.getData()))
        .pipe(map((e: any) => <any>e))
        .subscribe(data => {
            this.dialog.closeAll();
            this.ui.displayFlashMessage(<Array<Message>>data.messages);
        })
    }
}
