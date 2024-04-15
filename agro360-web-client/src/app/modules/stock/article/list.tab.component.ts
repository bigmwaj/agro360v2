
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTable } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs';
import { ArticleBean, ArticleSearchBean } from 'src/app/backed/bean.stock';
import { BeanList } from 'src/app/common/component/bean.list';
import { SharedModule } from 'src/app/common/shared.module';
import { IndexModalComponent } from '../unite/index.modal.component';
import { DeleteDialogComponent } from './delete.dialog.component';
import { MatDividerModule } from '@angular/material/divider';

@Component({
    standalone: true,
    imports: [
        IndexModalComponent,
        SharedModule,
        MatDividerModule
    ],
    selector: 'stock-article-list-tab',
    templateUrl: './list.tab.component.html'
})
export class ListTabComponent extends BeanList<ArticleBean> implements OnInit {

    @Input({required:true})
    editingBeans: ArticleBean[] = [];

    @Input({required:true})
    selectedTab: {index:number}

    searchForm: ArticleSearchBean;

    displayedColumns: string[] = [
        'select',
        'articleCode',
        'unite',
        'type',
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
        this.http.get(`stock/article/create-form`).subscribe(data => {
            this.editingBeans.push(<ArticleBean>data); 
            this.selectedTab.index = this.editingBeans.length;
        });
    }

    copyAction(bean: ArticleBean) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("copyFrom", bean.articleCode.value);

        this.http.get(`stock/article/create-form`, { params: queryParams }).subscribe(data => {
            this.editingBeans.push(<ArticleBean>data); 
            this.selectedTab.index = this.editingBeans.length;
        });
    }

    editAction(bean: ArticleBean) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("articleCode", bean.articleCode.value);

        this.http.get(`stock/article/edit-form`, { params: queryParams }).subscribe(data => {
            this.editingBeans.push(<ArticleBean>data); 
            this.selectedTab.index = this.editingBeans.length;
        });
    }

    deleteAction(bean: ArticleBean) {
        this.dialog.open(DeleteDialogComponent, { data: { articleCode:bean.articleCode.value} });
    }

    uniteAction() {
        this.dialog.open(IndexModalComponent);
    }
}