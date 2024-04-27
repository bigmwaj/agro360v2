import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { map } from 'rxjs';
import { CommandeBean, LigneBean } from 'src/app/backed/bean.av';
import { Message } from 'src/app/backed/message';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';
import { BeanTools } from 'src/app/modules/common/bean.tools';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { EditLigneListComponent } from './edit.ligne.list.component';
import { EditRemiseDialogComponent } from './edit.remise.dialog.component';
import { BeanEditTab } from '../../common/bean.edit.tab';
import { MatTooltipModule } from '@angular/material/tooltip';
import { DisplayReglementDialogComponent } from './display.reglement.dialog.component';

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
        private http: HttpClient,
        public override ui: UIService,
        public dialog: MatDialog) { 
        super(ui);
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

    piementComptantTotalAction() {
        this.bean.paiementComptant.value = this.bean.prixTotal.value - this.bean.cumulPaiement.value;
        this.bean.paiementComptant.valueChanged = true
    }
     
    saveAction() {
        this.http.post(`achat-vente/commande`, BeanTools.reviewBeanAction(this.bean))
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.ui.displayFlashMessage(<Array<Message>>data.messages);
                this.afterSaveAction(data.record)
            });
    }

    encaisserAction() {
        this.http.post(`achat-vente/commande/encaisser`, BeanTools.reviewBeanAction(this.bean))
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.ui.displayFlashMessage(<Array<Message>>data.messages);
                this.afterSaveAction(data.record)
            });
    } 
    
    protected override afterSaveAction(bean: CommandeBean): void {
        super.afterSaveAction(bean);
        this.ligneList.commande = bean
        this.ligneList.setData(bean.lignes)
    }

    afficherReglementAction() {
        this.dialog.open(DisplayReglementDialogComponent, { data: this.bean });
    }

    /* *****************
     * Fin des actions *
     * *****************
     */
    private isCreation(): boolean {
        return ClientOperationEnumVd.CREATE == this.bean.action;
    }

    private noDeleted = (e: LigneBean) => e.action != ClientOperationEnumVd.DELETE;
    private cumuler(lignes:Array<LigneBean>, mappeur:(e:LigneBean)=>number):number{
        return lignes
            .filter(this.noDeleted)
            .map(mappeur)
            .reduce((a, b) => a + b, 0);
    }

    private initPrix() {
        this.bean.prixTotal.value = this.cumuler(this.bean.lignes, e => e.prixTotalHT.value);
        this.bean.taxe.value = this.cumuler(this.bean.lignes, e => e.taxe.value);
        this.bean.remise.value = this.cumuler(this.bean.lignes, e => e.remise.value);
        this.bean.prixTotalHT.value = this.cumuler(this.bean.lignes, e => e.prixTotalHT.value);
    }

}
