import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { MatTable, MatTableModule } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs';
import { CaisseBean, CaisseIdBean, CaisseSearchBean } from 'src/app/backed/bean.stock';
import { BeanList } from 'src/app/common/bean.list';
import { CommonUtlis } from 'src/app/common/utils/common.utils';
import { ChangeStatusDialogComponent } from './change-status.dialog.component';
import { DeleteDialogComponent } from './delete.dialog.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { SharedModule } from 'src/app/common/shared.module';

@Component({
    standalone: true,
    imports: [
        CommonModule,
        MatButtonModule,
        MatIconModule,
        MatDialogModule,
        MatCheckboxModule,
        MatTableModule,
        ChangeStatusDialogComponent,
        DeleteDialogComponent,
        SharedModule
    ],
    selector: 'stock-caisse-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent extends BeanList<CaisseBean> implements OnInit {

    searchForm: CaisseSearchBean;

    displayedColumns: string[] = [
        'select',
        'magasin',
        'agent',
        'journee',
        'status',
        'note',
        'actions'
    ];

    @ViewChild(MatTable)
    table: MatTable<CaisseBean>;

    constructor(
        private router: Router,
        private route: ActivatedRoute,
        private http: HttpClient,
        public dialog: MatDialog
    ) {
        super();
    }

    override getViewChild(): MatTable<CaisseBean> {
        return this.table;
    }

    getKeyLabel(bean: CaisseBean): string {
        return bean.journee.value;
    }

    ngOnInit(): void {
        this.resetSearchFormAction()
    }

    resetSearchFormAction() {
        this.http
            .get(`${CommonUtlis.BASE_URL}/stock/caisse/search-form`)
            .subscribe(data => {
                this.searchForm = <CaisseSearchBean>data;
                this.searchAction();
            });
    }

    searchAction() {
        this.http
            .get(`${CommonUtlis.BASE_URL}/stock/caisse`, { params: CommonUtlis.encodeQuery(this.searchForm) })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.setData(data.records);
            });
    }

    addAction() {
        this.router.navigate(['create'], { relativeTo: this.route })
    }

    editAction(bean: CaisseBean) {
        this.router.navigate(
            [
                'edit',
                bean.magasin.magasinCode.value,
                bean.agent.tiersCode.value,
                bean.journee.value
            ],
            {
                relativeTo: this.route
            }
        )
    }

    private mapToBeanId(bean: CaisseBean): CaisseIdBean {
        return {
            magasin: bean.magasin.magasinCode.value,
            agent: bean.agent.tiersCode.value,
            journee: bean.journee.value
        };
    }

    changeStatusAction(bean: CaisseBean) {
        this.dialog.open(ChangeStatusDialogComponent, { data: this.mapToBeanId(bean) });
    }

    deleteAction(bean: CaisseBean) {
        this.dialog.open(DeleteDialogComponent, { data: this.mapToBeanId(bean) });
    }

    onDelete(bean: CaisseBean) {
        this.removeItem(bean);
    }
}
