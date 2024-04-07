import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTable } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs';
import { CaisseBean, CaisseIdBean, CaisseSearchBean } from 'src/app/backed/bean.stock';
import { BeanList } from 'src/app/common/component/bean.list';
import { CommonUtlis } from 'src/app/common/utils/common.utils';
import { ChangeStatusDialogComponent } from './change-status.dialog.component';
import { DeleteDialogComponent } from './delete.dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { SharedModule } from 'src/app/common/shared.module';

@Component({
    standalone: true,
    imports: [
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
        'Partner',
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
            .get(`stock/caisse/search-form`)
            .subscribe(data => {
                this.searchForm = <CaisseSearchBean>data;
                this.searchAction();
            });
    }

    searchAction() {
        this.http
            .get(`stock/caisse`, { params: CommonUtlis.encodeQuery(this.searchForm) })
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
                bean.Partner.partnerCode.value,
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
            Partner: bean.Partner.partnerCode.value,
            journee: bean.journee.value
        };
    }

    changeStatusAction(bean: CaisseBean) {
        const dialog = this.dialog.open(ChangeStatusDialogComponent, { data: this.mapToBeanId(bean) });
        const inst = dialog.componentInstance;
        dialog.afterClosed().subscribe(() => {
            if(inst.success){this.searchAction();}
        });
    }

    deleteAction(bean: CaisseBean) {
        const dialog = this.dialog.open(DeleteDialogComponent, { data: this.mapToBeanId(bean) });
        const inst = dialog.componentInstance;
        dialog.afterClosed().subscribe(() => {
            if(inst.success){this.searchAction();}
        });
    }
}
