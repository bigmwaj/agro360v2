
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatToolbarModule } from '@angular/material/toolbar';
import { map } from 'rxjs';
import { UniteBean, UniteSearchBean } from 'src/app/backed/bean.stock';
import { Message } from 'src/app/backed/message';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';
import { BeanList } from 'src/app/modules/common/bean/bean.list';
import { BeanTools } from 'src/app/modules/common/bean/bean.tools';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanPagedListTab } from '../../common/bean/bean.paged.list.tab';
import { BeanPagedList } from '../../common/bean/bean.paged.list';

@Component({
    standalone: true,
    imports: [
        SharedModule, 
        MatToolbarModule, 
    ],
    selector: 'stock-unite-index-modal',
    templateUrl: './index.modal.component.html'
})
export class IndexModalComponent extends BeanPagedList<UniteBean, UniteSearchBean> implements OnInit {

    displayedColumns: string[] = [
        'select',
        'uniteCode',
        'description',
        'actions'
    ];

    constructor(
        private dialog: MatDialog,       
        public ui: UIService,
        public override http: HttpClient) {
        super(http)
    }
    
    getKeyLabel(bean: UniteBean): string {
        return bean.uniteCode.value;
    }

    ngOnInit(): void {
        this.resetSearchFormAction()
    }

    __add(queryParams: HttpParams) {
        this.http
            .get("stock/unite/create-form", { params: queryParams })
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
        this.http.post(`stock/unite`, BeanTools.reviewBeansAction(this.getData()))
        .pipe(map((e: any) => <any>e))
        .subscribe(data => {
            this.ui.displayFlashMessage(<Array<Message>>data.messages);
            this.dialog.closeAll();
        })
    }
    
    protected override getSearchFormUrl(): string {
        return `stock/unite/search-form`;
    }

    protected override getSearchUrl(): string {
        return `stock/unite`;
    }
    
    areBeansEqual(b1:UniteBean, b2:UniteBean){
        return b1 == b2 || b1.uniteCode.value == b2.uniteCode.value;
    }
}
