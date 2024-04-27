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

    ngOnInit(): void {
        this.resetSearchFormAction();
        this.breadcrumb = this.breadcrumb.addAndReturnChildItem('Liste des magasins');
    }

    addAction(bean?: MagasinBean) { 
        let queryParams = new HttpParams();
        if( bean ){
            queryParams = queryParams.append("copyFrom", bean.magasinCode.value);       
        }
        this.http.get(`stock/magasin/create-form`, { params: queryParams })
        .subscribe(data => this.appendTab(<MagasinBean>data));
    }

    editAction(bean: MagasinBean) {
        if( this.isAlreadLoaded(bean) ){
            this.displayTab(bean);
            return;
        }

        let queryParams = new HttpParams();
        queryParams = queryParams.append("magasinCode", bean.magasinCode.value);

        this.http.get(`stock/magasin/edit-form`, { params: queryParams })
        .subscribe(data => this.appendTab(<MagasinBean>data));
    }

    deleteAction(bean: MagasinBean) {
        // Open Dialog
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
}
