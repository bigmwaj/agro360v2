import { CommonModule } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatTable, MatTableModule } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs';
import { BonCommandeBean, BonCommandeSearchBean } from 'src/app/backed/bean.achat';
import { BeanList } from 'src/app/common/component/bean.list';
import { SharedModule } from 'src/app/common/shared.module';
import { MatSidenavModule } from '@angular/material/sidenav';
import { ChangeStatusDialogComponent } from './change-status.dialog.component';
import { DeleteDialogComponent } from './delete.dialog.component';

@Component({
    standalone: true,
    imports: [
        CommonModule,
        MatButtonModule,
        MatIconModule,
        MatDialogModule,
        MatCheckboxModule,
        MatTableModule,
        SharedModule,
        MatSidenavModule
    ],
    selector: 'achat-bonCommande-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent extends BeanList<BonCommandeBean> implements OnInit {

    searchForm: BonCommandeSearchBean;
    
    displayedColumns: string[] = [
        'select',
        'bonCommandeCode',
        'status',
        'livraison',
        'dateBonCommande',
        'magasin',
        'fournisseur',
        'description',
        'actions'
    ];

    @ViewChild(MatTable)
    public table: MatTable<BonCommandeBean>;
    
    constructor(private router: Router,
        private route: ActivatedRoute,
        private http: HttpClient,
        public dialog: MatDialog) {
        super()
    }

    getKeyLabel(bean: BonCommandeBean): string | number {
        return bean.bonCommandeCode.value;
    }

    override getViewChild(): MatTable<BonCommandeBean> {
        return this.table;
    }

    ngOnInit(): void {
        this.resetSearchFormAction()
    }

    resetSearchFormAction() {
        this.http
            .get(`achat/bon-commande/search-form`)
            .subscribe(data => {
                this.searchForm = <BonCommandeSearchBean>data;
                this.searchAction();
            });
    }

    searchAction() {
        let objJsonStr = JSON.stringify(this.searchForm);
        let objJsonB64 = btoa(objJsonStr);

        let queryParams = new HttpParams();
        queryParams = queryParams.append('q', objJsonB64);
        this.http
            .get(`achat/bon-commande`, { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.setData(data.records);
            });
    }

    addAction() {
        this.router.navigate(['create'], { relativeTo: this.route })
    }

    editAction(bean: BonCommandeBean) {
        this.router.navigate(['edit', bean.bonCommandeCode.value], { relativeTo: this.route })
    }

    copyAction(bean: BonCommandeBean) {
        this.router.navigate(['create'], { relativeTo: this.route, queryParams: { 'copyFrom': bean.bonCommandeCode.value } })
    }

    changeStatusAction(bean: BonCommandeBean) {
        this.dialog.open(ChangeStatusDialogComponent, { data: bean.bonCommandeCode.value });
    }

    deleteAction(bean: BonCommandeBean) {
        this.dialog.open(DeleteDialogComponent, { data: bean.bonCommandeCode.value });
    }

    onDelete(bean: BonCommandeBean) {
        this.removeItem(bean);
    }
}
