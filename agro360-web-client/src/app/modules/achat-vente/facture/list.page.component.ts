import { CommonModule } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTable, MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { map } from 'rxjs';
import { FactureBean, FactureSearchBean } from 'src/app/backed/bean.av';
import { BeanList } from 'src/app/common/component/bean.list';
import { SharedModule } from 'src/app/common/shared.module';
import { ChangeStatusDialogComponent } from './change-status.dialog.component';
import { DeleteDialogComponent } from './delete.dialog.component';

@Component({
    standalone: true,
    imports: [
        CommonModule,
        MatButtonModule,
        MatDialogModule,
        MatCheckboxModule,
        MatTableModule,
        SharedModule,
        MatSidenavModule,        
        MatToolbarModule, 
        MatIconModule
    ],
    selector: 'achat-vente-facture-list-page',
    templateUrl: './list.page.component.html'
})
export class ListPageComponent extends BeanList<FactureBean> implements OnInit {

    @Input({required:true})
    editingBeans: FactureBean[] = [];

    @Input({required:true})
    selectedTab: {index:number}

    searchForm: FactureSearchBean;
    
    displayedColumns: string[] = [
        'select',
        'factureCode',
        'status',
        'date',
        'type',
        'partner',
        'montant',
        'description',
        'actions'
    ];

    @ViewChild(MatTable)
    public table: MatTable<FactureBean>;
    
    constructor(
        private http: HttpClient,
        public dialog: MatDialog) {
        super()
    }

    getKeyLabel(bean: FactureBean): string | number {
        return bean.factureCode.value;
    }

    override getViewChild(): MatTable<FactureBean> {
        return this.table;
    }

    ngOnInit(): void {
        this.resetSearchFormAction()
    }

    resetSearchFormAction() {
        this.http
            .get(`achat-vente/facture/search-form`)
            .subscribe(data => {
                this.searchForm = <FactureSearchBean>data;
                this.searchAction();
            });
    }

    searchAction() {
        let objJsonStr = JSON.stringify(this.searchForm);
        let objJsonB64 = btoa(objJsonStr);

        let queryParams = new HttpParams();
        queryParams = queryParams.append('q', objJsonB64);
        this.http
            .get(`achat-vente/facture`, { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.setData(data.records);
            });
    }

    addAction() {        
        this.http.get(`achat-vente/facture/create-form`).subscribe(data => {
            this.editingBeans.push(<FactureBean>data); 
            this.selectedTab.index = this.editingBeans.length;
        });
    }

    copyAction(bean: FactureBean) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("copyFrom", bean.factureCode.value);

        this.http.get(`achat-vente/facture/create-form`, { params: queryParams }).subscribe(data => {
            this.editingBeans.push(<FactureBean>data); 
            this.selectedTab.index = this.editingBeans.length;
        });
    }

    editAction(bean: FactureBean) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("factureCode", bean.factureCode.value);

        this.http.get(`achat-vente/facture/edit-form`, { params: queryParams }).subscribe(data => {
            this.editingBeans.push(<FactureBean>data); 
            this.selectedTab.index = this.editingBeans.length;
        });
    }

    changeStatusAction(bean: FactureBean) {
        let dialogRef = this.dialog.open(ChangeStatusDialogComponent, { data: bean.factureCode.value });
        dialogRef.afterClosed().subscribe(result => {
            console.log(`TODO: Ne pas raffraichir si l'utilisateur n'a pas soumis le formulaire`)
            console.log(result)
            this.searchAction();
        }); 
    }

    deleteAction(bean: FactureBean) {
        let dialogRef = this.dialog.open(DeleteDialogComponent, { data: bean.factureCode.value });
        dialogRef.afterClosed().subscribe(result => {
            console.log(`TODO: Ne pas raffraichir si l'utilisateur n'a pas soumis le formulaire`)
            console.log(result)
            this.searchAction();
        }); 
    }

    onDelete(bean: FactureBean) {
        this.removeItem(bean);
    }
}
