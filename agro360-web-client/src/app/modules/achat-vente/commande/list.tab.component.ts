import { CommonModule } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { CommandeBean, CommandeSearchBean, PaiementBean } from 'src/app/backed/bean.av';
import { CommandeTypeEnumVd } from 'src/app/backed/vd.av';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanPagedListTab } from '../../common/bean.paged.list.tab';
import { ChangeStatusDialogComponent } from './change-status.dialog.component';
import { DeleteDialogComponent } from './delete.dialog.component';
import { ReceptionDialogComponent } from './reception/reception.dialog.component';
import { RetourDialogComponent } from './retour/retour.dialog.component';
import { ReglementDialogComponent } from '../common/reglement.dialog.component';
import { map } from 'rxjs';
import { PaiementDialogComponent } from '../common/paiement.dialog.component';

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
        MatTooltipModule,
        MatDividerModule
    ],
    selector: 'achat-vente-commande-list-tab',
    templateUrl: './list.tab.component.html'
})
export class ListTabComponent extends BeanPagedListTab<CommandeBean, CommandeSearchBean> implements OnInit {
   
    @Input({required:true})
    module:string;

    partnerLabel: string;
    
    displayedColumns: string[] = [
        'select',
        'commandeCode',
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

    getKeyLabel(bean: CommandeBean): string | number {
        return bean.commandeCode.value;
    }

    protected override initSearchForm(sf: CommandeSearchBean) : CommandeSearchBean{
        if( this.module != null ){            
            sf.type.editable = false;
            switch(this.module){
                case "vente": 
                sf.type.value = CommandeTypeEnumVd.VENTE;
                break;

                case "achat": 
                sf.type.value = CommandeTypeEnumVd.ACHAT;
                break;
            }                    
        }

        return sf;
    }

    override ngOnInit(): void {
        super.ngOnInit();
        
        this.breadcrumb = this.breadcrumb.addAndReturnChildItem('Liste des commandes');
        switch(this.module){
            case 'vente': this.partnerLabel = 'Client'; break;
            case 'achat': this.partnerLabel = 'Fournisseur'; break;
            default:
                throw new Error(`Aucun type de partenaire n'a été configuré pour le module ${this.module}`)
        }
    }

    override areBeansEqual(b1:CommandeBean, b2:CommandeBean){
        return b1 == b2 || b1.commandeCode.value == b2.commandeCode.value;
    }

    changeStatusAction(bean: CommandeBean) {
        let dialogRef = this.dialog.open(ChangeStatusDialogComponent, { data: {commandeCode: bean.commandeCode.value} });
        dialogRef.afterClosed().subscribe(result => {
            if( result ){
                this.replaceItemWith(bean, result);
            }
        });  
    }

    deleteAction(bean: CommandeBean) {
        let dialogRef = this.dialog.open(DeleteDialogComponent, { data: bean.commandeCode.value });
        dialogRef.afterClosed().subscribe(result => {
            if( result ){
                this.removeItem(bean);
            }
        });  
    }   

    afficherReglementAction(bean:CommandeBean) {        
        let queryParams = new HttpParams();
        queryParams = queryParams.append("commandeCode", bean.commandeCode.value);
        this.dialog.open(ReglementDialogComponent, {data: {bean:bean, queryParams:queryParams, url: `achat-vente/commande/reglement`} });
    }  
        
    retourAction(bean: CommandeBean) {
        this.dialog.open(RetourDialogComponent, { data: bean });
    }  
    
    receptionAction(bean: CommandeBean) {
        this.dialog.open(ReceptionDialogComponent, { data: bean });
    }

    initPaiementAction(bean: CommandeBean) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("commandeCode", bean.commandeCode.value);

        this.http.get(`achat-vente/commande/init-paiement`, {params:queryParams}) 
        .pipe(map((e: any) => <any>e))
        .subscribe(data => {
            this.initPaiement(bean, data.records);
        })
    }

    private initPaiement(bean: CommandeBean, paiements:Array<PaiementBean>) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("commandeCode", bean.commandeCode.value);
        let dialogRef = this.dialog.open(PaiementDialogComponent, { data: {
                    bean:bean, 
                    paiements: paiements, 
                    saveUrl: `achat-vente/commande/encaisser`,
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
        return `achat-vente/commande/search-form`;
    }

    protected override getSearchUrl(): string {
        return `achat-vente/commande`;
    }  

    protected override getEditFormUrl(): string {
        return `achat-vente/commande/edit-form`;
    }

    protected override getCreateFormUrl(): string {
        return `achat-vente/commande/create-form`;
    }
    
    protected override getEditQueryParam(bean: CommandeBean): HttpParams {
        let queryParams = new HttpParams();
        return queryParams.append("commandeCode", bean.commandeCode.value);
    }
    
    protected override getCreateQueryParam(bean: CommandeBean): HttpParams {
        let queryParams = new HttpParams();  
        let type:CommandeTypeEnumVd;
        if( bean ){
            queryParams = queryParams.append("copyFrom", bean.commandeCode.value);
            type = bean.type.value
        }else{
            switch(this.module){
                case 'vente': type = CommandeTypeEnumVd.VENTE; break;
                case 'achat': type = CommandeTypeEnumVd.ACHAT; break;
                default:
                    throw new Error(`Aucun type de commande n'a été configuré pour le module ${this.module}`)
            }
        }

        return queryParams.append("type", type);
    }
}
