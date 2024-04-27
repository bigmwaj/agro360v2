import { CommonModule } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTable, MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { map } from 'rxjs';
import { FactureBean, FactureSearchBean } from 'src/app/backed/bean.av';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanListTab } from '../../common/bean.list.tab';
import { ChangeStatusDialogComponent } from './change-status.dialog.component';
import { DeleteDialogComponent } from './delete.dialog.component';
import { BeanPagedListTab } from '../../common/bean.paged.list.tab';

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
export class ListTabComponent extends BeanPagedListTab<FactureBean, FactureSearchBean> implements OnInit {
    
    @Input({required:true})
    module:string;
    
    partnerLabel: string;
    
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
    
    constructor(
        private dialog: MatDialog,
        public override http: HttpClient,       
        public override ui: UIService) {
        super(http, ui)
    }

    getKeyLabel(bean: FactureBean): string | number {
        return bean.factureCode.value;
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

    areBeansEqual(b1:FactureBean, b2:FactureBean){
        return b1 == b2 || b1.factureCode.value == b2.factureCode.value;
    }

    addAction(bean?: FactureBean) {   
        
        let queryParams = new HttpParams();
        if( bean ){
            queryParams = queryParams.append("copyFrom", bean.factureCode.value);     
        }
        this.http.get(`achat-vente/facture/create-form`, { params: queryParams })
        .subscribe(data => this.appendTab(<FactureBean>data));
    }

    editAction(bean: FactureBean) {
        if( this.isAlreadLoaded(bean) ){
            this.displayTab(bean);
            return;
        }

        let queryParams = new HttpParams();
        queryParams = queryParams.append("factureCode", bean.factureCode.value);

        this.http.get(`achat-vente/facture/edit-form`, { params: queryParams })
        .subscribe(data => this.appendTab(<FactureBean>data));
    }

    changeStatusAction(bean: FactureBean) {
        let dialogRef = this.dialog.open(ChangeStatusDialogComponent, { data: bean.factureCode.value });
        dialogRef.afterClosed().subscribe(result => {

        }); 
    }

    deleteAction(bean: FactureBean) {
        let dialogRef = this.dialog.open(DeleteDialogComponent, { data: bean.factureCode.value });
        dialogRef.afterClosed().subscribe(result => {
            this.removeItem(bean);
        }); 
    }

    protected override getSearchFormUrl(): string {
        return `achat-vente/facture/search-form`;
    }

    protected override getSearchUrl(): string {
        return `achat-vente/facture`;
    }
}
