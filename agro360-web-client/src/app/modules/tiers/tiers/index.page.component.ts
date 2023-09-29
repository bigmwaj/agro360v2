import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTable } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs';
import { TiersBean, TiersSearchBean } from 'src/app/backed/bean.tiers';
import { BeanList } from 'src/app/common/component/bean.list';
import { IndexModalComponent as CategoryIndexModalComponent } from '../category/index.modal.component';
import { ChangeStatusDialogComponent } from './change-status.dialog.component';
import { DeleteDialogComponent } from './delete.dialog.component';
import { SharedModule } from 'src/app/common/shared.module';

const BASE_URL = "http://localhost:8080";

@Component({
    standalone: true,
    imports: [
        SharedModule
        
    ],
    selector: 'tiers-tiers-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent extends BeanList<TiersBean> implements OnInit {

    searchForm: TiersSearchBean;

    displayedColumns: string[] = [
        'select',
        'tiersCode',
        'tiersType',
        'status',
        'tiersName',
        'phone',
        'email',
        'country',
        'city',
        'address',
        'actions'
    ];

    @ViewChild(MatTable)
    public table: MatTable<TiersBean>;

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private http: HttpClient,
        public dialog: MatDialog
    ) {
        super()
    }

    override getViewChild(): MatTable<TiersBean> {
        return this.table;
    }

    getKeyLabel(bean: TiersBean): string {
        return bean.tiersCode.value;
    }

    ngOnInit(): void {
        this.resetSearchFormAction()
    }

    resetSearchFormAction() {
        this.http
            .get(BASE_URL + "/tiers/tiers/search-form")
            .subscribe(data => {
                this.searchForm = <TiersSearchBean>data;
                this.searchAction();
            });
    }

    searchAction() {
        let objJsonStr = JSON.stringify(this.searchForm);
        let objJsonB64 = btoa(objJsonStr);

        let queryParams = new HttpParams();
        queryParams = queryParams.append('q', objJsonB64);
        this.http
            .get(BASE_URL + "/tiers/tiers", { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.setData(data.records);
            });
    }

    addAction() {
        this.router.navigate(['create'], { relativeTo: this.route })
    }

    editAction(bean: TiersBean) {
        this.router.navigate(['edit', bean.tiersCode.value], { relativeTo: this.route })
    }

    copyAction(bean: TiersBean) {
        this.router.navigate(['create'], { relativeTo: this.route, queryParams: { 'copyFrom': bean.tiersCode.value } })
    }

    changeStatusAction(bean: TiersBean) {
        this.dialog.open(ChangeStatusDialogComponent, { data: { tiersCode: bean.tiersCode.value } });
    }

    deleteAction(bean: TiersBean) {
        this.dialog.open(DeleteDialogComponent, { data: { tiersCode: bean.tiersCode.value } });
    }

    categoryAction() {
        this.dialog.open(CategoryIndexModalComponent);
    }
}
