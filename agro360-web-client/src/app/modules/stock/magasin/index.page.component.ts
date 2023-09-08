import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTable } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs';
import { MagasinBean, MagasinSearchBean } from 'src/app/backed/bean.stock';
import { BeanList } from 'src/app/common/bean.list';
import { SharedModule } from 'src/app/common/shared.module';
import { IndexModalComponent } from '../unite/index.modal.component';

const BASE_URL = "http://localhost:8080";

@Component({
    standalone: true,
    imports: [
        IndexModalComponent,
        SharedModule
    ],
    selector: 'stock-magasin-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent extends BeanList<MagasinBean> implements OnInit {

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
            .get(BASE_URL + "/stock/magasin/search-form")
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
            .get(BASE_URL + "/stock/magasin", { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.setData(data.records);
            });
    }

    addAction() {
        this.router.navigate(['create'], { relativeTo: this.route })
    }

    editAction(bean: MagasinBean) {
        this.router.navigate(['edit', bean.magasinCode.value], { relativeTo: this.route })
    }

    copyAction(bean: MagasinBean) {
        this.router.navigate(['create'], { relativeTo: this.route, queryParams: { 'copyFrom': bean.magasinCode.value } })
    }

    deleteAction(bean: MagasinBean) {
        // Open Dialog
    }

    uniteAction() {
        this.dialog.open(IndexModalComponent);
    }
}
