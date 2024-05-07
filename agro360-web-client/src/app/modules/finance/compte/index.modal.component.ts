
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { map } from 'rxjs';
import { CompteBean, CompteSearchBean } from 'src/app/backed/bean.finance';
import { Message } from 'src/app/backed/message';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';
import { BeanList } from 'src/app/modules/common/bean.list';
import { BeanTools } from 'src/app/modules/common/bean.tools';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanPagedList } from '../../common/bean.paged.list';

@Component({
    standalone: true,
    imports: [
        SharedModule,
        MatTooltipModule,   
        MatToolbarModule, 
    ],
    selector: 'finance-compte-index-modal',
    templateUrl: './index.modal.component.html'
})
export class IndexModalComponent extends BeanPagedList<CompteBean, CompteSearchBean> implements OnInit {

    displayedColumns: string[] = [
        'select',
        'type',
        'compteCode',
        'partner',
        'libelle',
        'description',
        'actions'
    ];

    constructor(
        private dialog: MatDialog,
        public override http: HttpClient,       
        public ui: UIService) {
        super(http)
    }
    
    getKeyLabel(bean: CompteBean): string {
        return bean.compteCode.value;
    }

    ngOnInit(): void {
        this.resetSearchFormAction()
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
    
    protected override getSearchFormUrl(): string {
        return `finance/compte/search-form`;
    }

    protected override getSearchUrl(): string {
        return `finance/compte`;
    }
}
