
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { MatToolbarModule } from '@angular/material/toolbar';
import { ArticleBean, ArticleSearchBean } from 'src/app/backed/bean.stock';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanPagedListTab } from '../../common/bean.paged.list.tab';
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
        'select',
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

    ngOnInit(): void {
        this.resetSearchFormAction();
        this.breadcrumb = this.breadcrumb.addAndReturnChildItem('Liste des articles & services');
    }

    addAction() {        
        this.http.get(`stock/article/create-form`)
        .subscribe(data => this.appendTab(<ArticleBean>data));
    }

    copyAction(bean: ArticleBean) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("copyFrom", bean.articleCode.value);

        this.http.get(`stock/article/create-form`, { params: queryParams })
        .subscribe(data => this.appendTab(<ArticleBean>data));
    }

    editAction(bean: ArticleBean) {
        if( this.isAlreadLoaded(bean) ){
            this.displayTab(bean);
            return;
        }

        let queryParams = new HttpParams();
        queryParams = queryParams.append("articleCode", bean.articleCode.value);

        this.http.get(`stock/article/edit-form`, { params: queryParams })
        .subscribe(data => this.appendTab(<ArticleBean>data));
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
}
