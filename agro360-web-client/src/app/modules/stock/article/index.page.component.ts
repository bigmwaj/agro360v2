
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTable } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { ArticleBean, ArticleSearchBean } from 'src/app/backed/bean.stock';
import { IndexModalComponent } from '../unite/index.modal.component';
import { BeanList } from 'src/app/common/component/bean.list';
import { SharedModule } from 'src/app/common/shared.module';
import { DeleteDialogComponent } from './delete.dialog.component';
import { map } from 'rxjs';
import { UIService } from 'src/app/common/service/ui.service';

@Component({
    standalone: true,
    imports: [
        IndexModalComponent,
        SharedModule
    ],
    selector: 'stock-article-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent extends BeanList<ArticleBean> implements OnInit {

    searchForm: ArticleSearchBean;

    displayedColumns: string[] = [
        'select',
        'articleCode',
        'unite',
        'typeArticle',
        'description',
        'actions'
    ];

    @ViewChild(MatTable)
    table: MatTable<ArticleBean>;

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private http: HttpClient,       
        private dialog: MatDialog) {
        super()
    }

    override getViewChild(): MatTable<ArticleBean> {
        return this.table;
    }

    getKeyLabel(bean: ArticleBean): string {
        return bean.articleCode.value;
    }

    ngOnInit(): void {
        this.resetSearchFormAction()
    }

    resetSearchFormAction() {
        this.http        
            .get(`stock/article/search-form`)  
            .subscribe(data => {
                this.searchForm = <ArticleSearchBean>data;
                this.searchAction();
            });
    }

    searchAction() {
        let objJsonStr = JSON.stringify(this.searchForm);
        let objJsonB64 = btoa(objJsonStr);

        let queryParams = new HttpParams();
        queryParams = queryParams.append('q', objJsonB64);
        this.http
            .get(`stock/article`, { params: queryParams })  
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.setData(data.records);
            });
    }

    addAction() {
        this.router.navigate(['create'], { relativeTo: this.route })
    }

    editAction(bean: ArticleBean) {
        this.router.navigate(['edit', bean.articleCode.value], { relativeTo: this.route })
    }

    copyAction(bean: ArticleBean) {
        this.router.navigate(['create'], { relativeTo: this.route, queryParams: { 'copyFrom': bean.articleCode.value } })
    }

    deleteAction(bean: ArticleBean) {
        this.dialog.open(DeleteDialogComponent, { data: { articleCode:bean.articleCode.value} });
    }

    uniteAction() {
        this.dialog.open(IndexModalComponent);
    }
}
