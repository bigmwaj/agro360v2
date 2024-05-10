import { CommonModule } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { FactureBean, FactureSearchBean, PaiementBean } from 'src/app/backed/bean.av';
import { FactureTypeEnumVd } from 'src/app/backed/vd.av';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanPagedListTab } from '../../common/bean.paged.list.tab';
import { ChangeStatusDialogComponent } from './change-status.dialog.component';
import { DeleteDialogComponent } from './delete.dialog.component';
import { ReglementDialogComponent } from '../common/reglement.dialog.component';
import { MatDividerModule } from '@angular/material/divider';
import { map } from 'rxjs';
import { PaiementDialogComponent } from '../common/paiement.dialog.component';
import { TaxeListDialogComponent } from './taxe.list.dialog.component';

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
        MatTooltipModule,
        MatDividerModule
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
        'taxe',
        'remise',
        'prixTotal',
        'cumulPaiement',
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

    protected override initSearchForm(sf: FactureSearchBean) : FactureSearchBean{
        if( this.module != null ){            
            sf.type.editable = false;
            switch(this.module){
                case "vente": 
                sf.type.value = FactureTypeEnumVd.VENTE;
                break;

                case "achat": 
                sf.type.value = FactureTypeEnumVd.ACHAT;
                break;
            }                    
        }

        return sf;
    }

    override ngOnInit(): void {
        super.ngOnInit();
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

    afficherReglementAction(bean: FactureBean) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("factureCode", bean.factureCode.value);
        this.dialog.open(ReglementDialogComponent, { data: {bean:bean, queryParams:queryParams, url: `achat-vente/facture/reglement`} });
    }

    listerTaxesAction(bean: FactureBean) {
        this.dialog.open(TaxeListDialogComponent, { 
            data: bean
        });
    }

    initPaiementAction(bean: FactureBean) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("factureCode", bean.factureCode.value);

        this.http.get(`achat-vente/facture/init-paiement`, {params:queryParams}) 
        .pipe(map((e: any) => <any>e))
        .subscribe(data => {
            this.initPaiement(bean, data.records);
        })
    }

    private initPaiement(bean: FactureBean, paiements:Array<PaiementBean>) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("factureCode", bean.factureCode.value);
        let dialogRef = this.dialog.open(PaiementDialogComponent, { data: {
                    bean:bean, 
                    paiements: paiements, 
                    saveUrl: `achat-vente/facture/encaisser`,
                    saveParams:queryParams
                } 
            });
            dialogRef.afterClosed().subscribe(result => {
                if( result ){
                    this.replaceItemWith(bean, result);
                }
        }); 
    } 

    protected override getSearchFormUrl(): string {
        return `achat-vente/facture/search-form`;
    }

    protected override getSearchUrl(): string {
        return `achat-vente/facture`;
    }

    protected override getEditFormUrl(): string {
        return `achat-vente/facture/edit-form`;
    }

    protected override getCreateFormUrl(): string {
        return `achat-vente/facture/create-form`;
    }
    
    protected override getEditQueryParam(bean: FactureBean): HttpParams {
        let queryParams = new HttpParams();
        return queryParams.append("factureCode", bean.factureCode.value);
    }

    protected override getCreateQueryParam(option?: any): HttpParams {
        let queryParams = new HttpParams();  
        let type : FactureTypeEnumVd;
        if( option?.bean ){
            queryParams = queryParams.append("copyFrom", option.bean.factureCode.value);
            type = option.bean.type.value
        }else{
            switch(this.module){
                case 'vente': type = FactureTypeEnumVd.VENTE; break;
                case 'achat': type = FactureTypeEnumVd.ACHAT; break;
                default:
                    throw new Error(`Aucun type de facture n'a été configuré pour le module ${this.module}`)
            }
        }

        return queryParams.append("type", type);
    }
}
