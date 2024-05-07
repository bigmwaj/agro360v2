import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTooltipModule } from '@angular/material/tooltip';
import { CommandeBean, LigneBean, PaiementBean } from 'src/app/backed/bean.av';
import { CommandeStatusEnumVd } from 'src/app/backed/vd.av';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanEditTab } from '../../common/bean.edit.tab';
import { PaiementDialogComponent } from '../common/paiement.dialog.component';
import { ChangeStatusDialogComponent } from './change-status.dialog.component';
import { EditLigneListComponent } from './ligne/edit.ligne.list.component';
import { ReglementDialogComponent } from '../common/reglement.dialog.component';

@Component({
    standalone: true,
    imports: [
        SharedModule,
        EditLigneListComponent,
        MatTooltipModule
    ],
    selector: 'achat-vente-commande-edit-tab',
    templateUrl: './edit.tab.component.html'
})
export class EditTabComponent extends BeanEditTab<CommandeBean> implements OnInit{
    
    @Input({required:true})
    module:string;

    @ViewChild(EditLigneListComponent)
    ligneList: EditLigneListComponent

    partnerLabel: string

    constructor(
        public override http: HttpClient,
        public override ui: UIService,
        public dialog: MatDialog) { 
        super(ui, http);
    }
    
    ngOnInit(): void {
        switch(this.module){
            case 'vente': this.partnerLabel = 'Client'; break;
            case 'achat': this.partnerLabel = 'Fournisseur'; break;
            default:
                throw new Error(`Aucun type de partenaire n'a été configuré pour le module ${this.module}`)
        }
        let title;
        if (this.isCreation()) {
            title = `Création de la commande ${this.bean.commandeCode.value}`
        } else {                
            title = `Édition de la commande ${this.bean.commandeCode.value}`
        }

        this.breadcrumb = this.breadcrumb.addAndReturnChildItem(title)
        
    }

    /**************************************************
     * Liste des évènements générés par l'utilisateur *
     * ************************************************
     */

    /**
     * Cette évènement est enregistré au niveau des lignes
     * de la commande de même que la commande et permet de mettre à jour les prix total 
     * de la commande lorsque l'on modifie une information influencant le prix
     */
    updatePrixEvent() {
        this.initPrix()
    }

    /* ********************
     * Fin des évènements *
     * ********************
     */

    /******************************************************
     * Liste des actions que peut exécuter un utilisateur *
     * ****************************************************
     */
     override getSaveQueryParam(option?:any){
        let queryParams = super.getSaveQueryParam();
        if( option?.processPaiement ){
            queryParams = queryParams.append('processPaiement', option.processPaiement);
        }
        return queryParams;
     }

     override saveUrl(): string {
         return `achat-vente/commande`;
     }

    protected override afterSaveAction(bean: CommandeBean, option?:any): void {
        super.afterSaveAction(bean, option);

        this.ligneList.commande = bean
        this.ligneList.setData(bean.lignes);
        if( option?.processPaiement && option?.data ){
            this.initPaiement(bean, option?.data.records)
        }

        if( option?.terminer ){
            this.changerStatus(CommandeStatusEnumVd.ATAP, `Etes vous sûr de vouloir terminer la commande? <br/>La commande sera placée en attente approbation.`)
        }
    }

    private changerStatus(status: CommandeStatusEnumVd, message:string){
        let dialogRef = this.dialog.open(ChangeStatusDialogComponent, { data: {
                commandeCode: this.bean.commandeCode.value,
                status:status,
                message:message
            } 
        });
        dialogRef.afterClosed().subscribe(result => {
            if( result ){
                super.afterSaveAction(result);
            }
        });  
    }
    
    annulerAction(){
        this.changerStatus(CommandeStatusEnumVd.AANN, `Etes vous sur de vouloir annuler la commande? <br/>La commande sera placée en attente annulation.`)
    }

    private initPaiement(bean: CommandeBean, paiements:Array<PaiementBean>) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("commandeCode", this.bean.commandeCode.value);

        let dialogRef = this.dialog.open(PaiementDialogComponent, { data: {
                    bean:bean, 
                    paiements: paiements, 
                    saveUrl: `achat-vente/commande/encaisser`,
                    saveParams:queryParams
                } 
            });
            dialogRef.afterClosed().subscribe(result => {
                if( result ){
                    this.afterSaveAction(result);
                }
        }); 
    } 

    afficherReglementAction() {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("commandeCode", this.bean.commandeCode.value);
        this.dialog.open(ReglementDialogComponent, { data: {queryParams:queryParams, url: `achat-vente/commande/reglement`} });
    }

    private noDeleted = (e: LigneBean) => e.action != ClientOperationEnumVd.DELETE;

    private cumuler(lignes:Array<LigneBean>, mappeur:(e:LigneBean)=>number):number{
        return lignes
            .filter(this.noDeleted)
            .map(mappeur)
            .reduce((a, b) => a + b, 0);
    }

    private initPrix() {
        this.bean.prixTotalHT.value = this.cumuler(this.bean.lignes, e => e.prixTotalHT.value);
        this.bean.remise.value = this.cumuler(this.bean.lignes, e => e.remise.value);
        this.bean.taxe.value = this.cumuler(this.bean.lignes, e => e.taxe.value);
        this.bean.prixTotal.value = this.cumuler(this.bean.lignes, e => e.prixTotalHT.value);
    }
}
