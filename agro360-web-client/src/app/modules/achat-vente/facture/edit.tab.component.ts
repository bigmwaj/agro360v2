import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { FactureBean, PaiementBean } from 'src/app/backed/bean.av';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanEditTab } from '../../common/bean.edit.tab';
import { ReglementDialogComponent } from '../common/reglement.dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { PaiementDialogComponent } from '../common/paiement.dialog.component';
import { map } from 'rxjs';
import { MatTooltipModule } from '@angular/material/tooltip';
import { TaxeListDialogComponent } from './taxe.list.dialog.component';

@Component({
    standalone: true,
    imports: [
        SharedModule,
        MatTooltipModule,
    ],
    selector: 'achat-vente-facture-edit-tab',
    templateUrl: './edit.tab.component.html'
})
export class EditTabComponent extends BeanEditTab<FactureBean> implements OnInit {
    
    @Input({required:true})
    module:string;

    partnerLabel: string;

    constructor(
        private dialog: MatDialog,
        public override http: HttpClient,
        public override ui: UIService){ 
        super(ui, http);
    }
    
    ngOnInit(): void {
        switch(this.module){
            case 'vente': this.partnerLabel = 'Client'; break;
            case 'achat': this.partnerLabel = 'Fournisseur'; break;
            default:
                throw new Error(`Aucun type de partenaire n'a été configuré pour le module ${this.module}`)
        }
        let title:string
        if (this.isCreation()) {
            title = `Création de la facture ${this.bean.factureCode.value}`
        } else {
            title = `Édition de la facture ${this.bean.factureCode.value}`
        }
        this.breadcrumb = this.breadcrumb.addAndReturnChildItem(title)        
    }

    protected override afterSaveAction(bean: FactureBean, option?:any): void {
        super.afterSaveAction(bean, option);

        if( option?.processPaiement && option?.data ){
            this.initPaiement(bean, option?.data.records)
        }
    }

    override saveUrl():string {
        return `achat-vente/facture`;
    }

    afficherReglementAction() {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("factureCode", this.bean.factureCode.value);
        this.dialog.open(ReglementDialogComponent, { data: {bean:this.bean, queryParams:queryParams, url: `achat-vente/facture/reglement`} });
    }

    listerTaxesAction() {
        this.dialog.open(TaxeListDialogComponent, { 
            data: this.bean
        });
    }

    initPaiementAction() {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("factureCode", this.bean.factureCode.value);

        this.http.get(`achat-vente/facture/init-paiement`, {params:queryParams}) 
        .pipe(map((e: any) => <any>e))
        .subscribe(data => {
            this.initPaiement(this.bean, data.records);
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
                    this.afterSaveAction(result);
                }
        }); 
    }

    override getSaveQueryParam(option?:any){
        let queryParams = super.getSaveQueryParam();
        if( option?.processPaiement ){
            queryParams = queryParams.append('processPaiement', option.processPaiement);
        }
        return queryParams;
     }

}
