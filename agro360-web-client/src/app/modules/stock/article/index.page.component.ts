
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTable } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs';
import { ArticleBean, ArticleSearchBean } from 'src/app/backed/bean.stock';
import { IndexModalComponent } from '../unite/index.modal.component';
import { BeanList } from 'src/app/common/component/bean.list';
import { SharedModule } from 'src/app/common/shared.module';

const BASE_URL = "http://localhost:8080";

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

    constructor(private router: Router,
        private route: ActivatedRoute,
        private http: HttpClient,
        public dialog: MatDialog) {
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
            .get(BASE_URL + "/stock/article/search-form")
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
            .get(BASE_URL + "/stock/article", { params: queryParams })
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

    }

    uniteAction() {
        this.dialog.open(IndexModalComponent);
    }
}
