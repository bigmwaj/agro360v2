import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTable } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs';
import { MagasinBean, MagasinSearchBean } from 'src/app/backed/bean.stock';
import { BeanList } from 'src/app/common/component/bean.list';
import { SharedModule } from 'src/app/common/shared.module';
import { IndexModalComponent } from '../unite/index.modal.component';

@Component({
    standalone: true,
    imports: [
        IndexModalComponent,
        SharedModule
    ],
    selector: 'stock-magasin-list-tab',
    templateUrl: './list.tab.component.html'
})
export class ListTabComponent extends BeanList<MagasinBean> implements OnInit {

    @Input({required:true})
    editingBeans: MagasinBean[] = [];

    @Input({required:true})
    selectedTab: {index:number}

    searchForm: MagasinSearchBean;

    @ViewChild(MatTable)
    table: MatTable<MagasinBean>;

    displayedColumns: string[] = [
        'select',
        'magasinCode',
        'description',
        'actions'
    ];

    constructor(private router: Router,
        private route: ActivatedRoute,
        private http: HttpClient,
        public dialog: MatDialog) {
            super()
    }

    override getViewChild(): MatTable<MagasinBean> {
        return this.table;
    }

    getKeyLabel(bean: MagasinBean): string {
        return bean.magasinCode.value;
    }

    ngOnInit(): void {
        this.resetSearchFormAction()
    }

    resetSearchFormAction() {
        this.http
            .get("stock/magasin/search-form")
            .subscribe(data => {
                this.searchForm = <MagasinSearchBean>data;
                this.searchAction();
            });
    }

    searchAction() {
        let objJsonStr = JSON.stringify(this.searchForm);
        let objJsonB64 = btoa(objJsonStr);

        let queryParams = new HttpParams();
        queryParams = queryParams.append('q', objJsonB64);
        this.http
            .get("stock/magasin", { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.setData(data.records);
            });
    }

    addAction() {        
        this.http.get(`stock/magasin/create-form`).subscribe(data => {
            this.editingBeans.push(<MagasinBean>data); 
            this.selectedTab.index = this.editingBeans.length;
        });
    }

    copyAction(bean: MagasinBean) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("copyFrom", bean.magasinCode.value);

        this.http.get(`stock/magasin/create-form`, { params: queryParams }).subscribe(data => {
            this.editingBeans.push(<MagasinBean>data); 
            this.selectedTab.index = this.editingBeans.length;
        });
    }

    editAction(bean: MagasinBean) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("magasinCode", bean.magasinCode.value);

        this.http.get(`stock/magasin/edit-form`, { params: queryParams }).subscribe(data => {
            this.editingBeans.push(<MagasinBean>data); 
            this.selectedTab.index = this.editingBeans.length;
        });
    }

    deleteAction(bean: MagasinBean) {
        // Open Dialog
    }

    uniteAction() {
        this.dialog.open(IndexModalComponent);
    }
}
