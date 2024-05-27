
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ArticleBean, ArticleSearchBean } from 'src/app/backed/bean.stock';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanPagedListTab } from '../../common/bean/bean.paged.list.tab';
import { IndexModalComponent } from '../unite/index.modal.component';
import { DeleteDialogComponent } from './delete.dialog.component';

@Component({
    standalone: true,
    imports: [
        IndexModalComponent,
        SharedModule,
        MatDividerModule,   
        MatToolbarModule, 
    ],
    selector: 'stock-article-list-tab',
    templateUrl: './list.tab.component.html'
})
export class ListTabComponent extends BeanPagedListTab<ArticleBean, ArticleSearchBean> implements OnInit {

    displayedColumns: string[] = [
        'articleCode',
        'unite',
        'type',
        'description',
        'actions'
    ];

    constructor(
        private dialog: MatDialog,
        public override http: HttpClient,       
        public override ui: UIService) {
        super(http, ui)
    }

    areBeansEqual(b1:ArticleBean, b2:ArticleBean){
        return b1 == b2 || b1.articleCode.value == b2.articleCode.value;
    }

    getKeyLabel(bean: ArticleBean): string {
        return bean.articleCode.value;
    }

    override ngOnInit(): void {
        super.ngOnInit();
        this.breadcrumb = this.breadcrumb.addAndReturnChildItem('Liste des articles & services');
    }

    deleteAction(bean: ArticleBean) {
        this.dialog.open(DeleteDialogComponent, { data: { articleCode:bean.articleCode.value} });
    }

    uniteAction() {
        this.dialog.open(IndexModalComponent);
    }

    protected override getSearchFormUrl(): string {
        return `stock/article/search-form`;
    }

    protected override getSearchUrl(): string {
        return `stock/article`;
    }

    protected override getEditFormUrl(): string {
        return `stock/article/edit-form`;
    }

    protected override getCreateFormUrl(): string {
        return `stock/article/create-form`;
    }

    protected override getEditQueryParam(bean: ArticleBean): HttpParams {
        let queryParams = new HttpParams();
        return queryParams.append("articleCode", bean.articleCode.value);
    }
    
    protected override getCreateQueryParam(option?: any): HttpParams {
        let queryParams = new HttpParams();
        if( option?.bean) {
            queryParams = queryParams.append("copyFrom", option.bean.articleCode.value);    
        }

        return queryParams;
    }
}
