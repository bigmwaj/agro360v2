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
import { BreadcrumbItem, UIService } from 'src/app/common/service/ui.service';
import { MatTooltipModule } from '@angular/material/tooltip';

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
        MatTooltipModule
    ],
    selector: 'achat-vente-facture-list-tab',
    templateUrl: './list.tab.component.html'
})
export class ListTabComponent extends BeanList<FactureBean> implements OnInit {

    @Input({required:true})
    editingBeans: FactureBean[] = [];

    @Input({required:true})
    selectedTab: {index:number}    

    @Input({required:true})
    breadcrumb:BreadcrumbItem
    
    @Input({required:true})
    module:string;
    
    partnerLabel: string;

    searchForm: FactureSearchBean;
    
    displayedColumns: string[] = [
        'select',
        'factureCode',
        'status',
        'date',
        'type',
        'partner',
        'montant',
        'actions'
    ];

    @ViewChild(MatTable)
    public table: MatTable<FactureBean>;
    
    constructor(
        private http: HttpClient,
        public dialog: MatDialog,
        private ui: UIService) {
        super()
    }

    getKeyLabel(bean: FactureBean): string | number {
        return bean.factureCode.value;
    }

    override getViewChild(): MatTable<FactureBean> {
        return this.table;
    }
        
    ngAfterViewInit(): void {
        this.refreshPageTitle()
    }

    refreshPageTitle():void{
        this.ui.setBreadcrumb(this.breadcrumb)
    }

    ngOnInit(): void {
        this.resetSearchFormAction()
        this.breadcrumb = this.breadcrumb.addAndReturnChildItem('Liste des factures');
        switch(this.module){
            case 'vente': this.partnerLabel = 'Client'; break;
            case 'achat': this.partnerLabel = 'Fournisseur'; break;
            default:
                throw new Error(`Aucun type de partenaire n'a été configuré pour le module ${this.module}`)
        }
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

    private getEditingIndex(bean:FactureBean){
        return this.editingBeans.findIndex(e => bean.factureCode.value == e.factureCode.value)
    }

    addAction(bean?: FactureBean) {   
        
        let queryParams = new HttpParams();
        if( bean ){
            queryParams = queryParams.append("copyFrom", bean.factureCode.value);     
        }
        this.http.get(`achat-vente/facture/create-form`, { params: queryParams }).subscribe(data => {
            this.editingBeans.push(<FactureBean>data); 
            this.selectedTab.index = this.editingBeans.length;
        });
    }

    editAction(bean: FactureBean) {
        const index = this.getEditingIndex(bean);
        if( index >= 0 ){
            this.selectedTab.index = index + 1;
            return;
        }
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
