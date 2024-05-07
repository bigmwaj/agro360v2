import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MagasinBean, MagasinSearchBean } from 'src/app/backed/bean.stock';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanPagedListTab } from '../../common/bean.paged.list.tab';
import { IndexModalComponent } from '../unite/index.modal.component';

@Component({
    standalone: true,
    imports: [
        IndexModalComponent,
        SharedModule,           
        MatToolbarModule, 
    ],
    selector: 'stock-magasin-list-tab',
    templateUrl: './list.tab.component.html'
})
export class ListTabComponent extends BeanPagedListTab<MagasinBean, MagasinSearchBean> implements OnInit {

    displayedColumns: string[] = [
        'select',
        'magasinCode',
        'description',
        'actions'
    ];

    constructor(
        private dialog: MatDialog,
        public override http: HttpClient,       
        public override ui: UIService) {
        super(http, ui)
    }

    getKeyLabel(bean: MagasinBean): string {
        return bean.magasinCode.value;
    }
    
    areBeansEqual(b1:MagasinBean, b2:MagasinBean){
        return b1 == b2 || b1.magasinCode.value == b2.magasinCode.value;
    }

    override ngOnInit(): void {
        super.ngOnInit();
        this.breadcrumb = this.breadcrumb.addAndReturnChildItem('Liste des magasins');
    }

    deleteAction(bean: MagasinBean) {
        throw Error('Not implemented!');
    }

    uniteAction() {
        this.dialog.open(IndexModalComponent);
    }

    protected override getSearchFormUrl(): string {
        return `stock/magasin/search-form`;
    }

    protected override getSearchUrl(): string {
        return `stock/magasin`;
    }

    protected override getEditFormUrl(): string {
        return `stock/magasin/edit-form`;
    }

    protected override getCreateFormUrl(): string {
        return `stock/magasin/create-form`;
    }

    protected override getEditQueryParam(bean: MagasinBean): HttpParams {
        let queryParams = new HttpParams();
        return queryParams.append("magasinCode", bean.magasinCode.value);
    }
    
    protected override getCreateQueryParam(option?: any): HttpParams {
        let queryParams = new HttpParams();
        if( option?.bean) {
            queryParams = queryParams.append("copyFrom", option?.bean.magasinCode.value);    
        }

        return queryParams;
    }

}
