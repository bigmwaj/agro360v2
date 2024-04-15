import { CommonModule } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTable, MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { map } from 'rxjs';
import { CommandeBean, CommandeSearchBean } from 'src/app/backed/bean.av';
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
        MatIconModule,
        MatTabsModule,
    ],
    selector: 'achat-vente-commande-list-page',
    templateUrl: './list.page.component.html'
})
export class ListPageComponent extends BeanList<CommandeBean> implements OnInit {

    @Input({required:true})
    editingBeans: CommandeBean[] = [];

    @Input({required:true})
    selectedTab: {index:number}

    searchForm: CommandeSearchBean;
    
    displayedColumns: string[] = [
        'select',
        'commandeCode',
        'status',
        'date',
        'type',
        'magasin',
        'partner',
        'compte',
        'taxe',
        'remise',
        'prixTotal',
        'paiementComptant',
        'actions'
    ];

    @ViewChild(MatTable)
    public table: MatTable<CommandeBean>;
    
    constructor(
        private http: HttpClient,
        public dialog: MatDialog) {
        super()
    }

    getKeyLabel(bean: CommandeBean): string | number {
        return bean.commandeCode.value;
    }

    override getViewChild(): MatTable<CommandeBean> {
        return this.table;
    }

    ngOnInit(): void {
        this.resetSearchFormAction()
    }

    resetSearchFormAction() {
        this.http
            .get(`achat-vente/commande/search-form`)
            .subscribe(data => {
                this.searchForm = <CommandeSearchBean>data;
                this.searchAction();
            });
    }

    searchAction() {
        let objJsonStr = JSON.stringify(this.searchForm);
        let objJsonB64 = btoa(objJsonStr);

        let queryParams = new HttpParams();
        queryParams = queryParams.append('q', objJsonB64);
        this.http
            .get(`achat-vente/commande`, { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.setData(data.records);
            });
    }

    addAction() {        
        this.http.get(`achat-vente/commande/create-form`).subscribe(data => {
            this.editingBeans.push(<CommandeBean>data); 
            this.selectedTab.index = this.editingBeans.length;
        });
    }

    copyAction(bean: CommandeBean) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("copyFrom", bean.commandeCode.value);

        this.http.get(`achat-vente/commande/create-form`, { params: queryParams }).subscribe(data => {
            this.editingBeans.push(<CommandeBean>data); 
            this.selectedTab.index = this.editingBeans.length;
        });
    }

    editAction(bean: CommandeBean) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("commandeCode", bean.commandeCode.value);

        this.http.get(`achat-vente/commande/edit-form`, { params: queryParams }).subscribe(data => {
            this.editingBeans.push(<CommandeBean>data); 
            this.selectedTab.index = this.editingBeans.length;
        });
    }

    changeStatusAction(bean: CommandeBean) {
        let dialogRef = this.dialog.open(ChangeStatusDialogComponent, { data: bean.commandeCode.value });
        dialogRef.afterClosed().subscribe(result => {
            console.log(`TODO: Ne pas raffraichir si l'utilisateur n'a pas soumis le formulaire`)
            console.log(result)
            this.searchAction();
        });  
    }

    deleteAction(bean: CommandeBean) {
        let dialogRef = this.dialog.open(DeleteDialogComponent, { data: bean.commandeCode.value });
        dialogRef.afterClosed().subscribe(result => {
            console.log(`TODO: Ne pas raffraichir si l'utilisateur n'a pas soumis le formulaire`)
            console.log(result)
            this.searchAction();
        });  
    }

    onDelete(bean: CommandeBean) {
        this.removeItem(bean);
    }
}
