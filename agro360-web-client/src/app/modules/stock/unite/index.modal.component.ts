
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTable } from '@angular/material/table';
import { map } from 'rxjs';
import { UniteBean, UniteSearchBean } from 'src/app/backed/bean.stock';
import { EditActionEnumVd } from 'src/app/backed/vd.common';
import { BeanList } from 'src/app/common/component/bean.list';
import { BeanTools } from 'src/app/common/bean.tools';
import { SharedModule } from 'src/app/common/shared.module';
import { UIService } from 'src/app/common/service/ui.service';
import { MatDialog } from '@angular/material/dialog';
import { Message } from 'src/app/backed/message';

@Component({
    standalone: true,
    imports: [
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

    constructor(
        private http: HttpClient,
        public dialog: MatDialog,
        private ui: UIService) {
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
            .get("stock/unite/search-form")
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
            .get("stock/unite", { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.setData(data.records);
            });
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
        this.http.post(`stock/unite`, BeanTools.reviewBeansAction(this.getData()))
        .pipe(map((e: any) => <any>e))
        .subscribe(data => {
            this.ui.displayFlashMessage(<Array<Message>>data.messages);
            this.dialog.closeAll();
        })
    }
}
