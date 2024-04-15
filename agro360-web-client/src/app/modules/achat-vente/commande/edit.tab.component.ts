import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { map } from 'rxjs';
import { CommandeBean, LigneBean } from 'src/app/backed/bean.av';
import { Message } from 'src/app/backed/message';
import { EditActionEnumVd } from 'src/app/backed/vd.common';
import { BeanTools } from 'src/app/common/bean.tools';
import { UIService } from 'src/app/common/service/ui.service';
import { SharedModule } from 'src/app/common/shared.module';
import { CoreUtils } from '../../core/core.utils';
import { FinanceService } from '../../finance/finance.service';
import { StockService } from '../../stock/stock.service';
import { EditLigneListComponent } from './edit.ligne.list.component';
import { EditRemiseDialogComponent } from './edit.remise.dialog.component';

@Component({
    standalone: true,
    imports: [
        SharedModule,
        EditLigneListComponent,
    ],
    selector: 'achat-vente-commande-edit-tab',
    templateUrl: './edit.tab.component.html'
})
export class EditTabComponent implements OnInit {

    @Input({required:true})
    bean: CommandeBean;

    pageTitle: string = "Edition";

    constructor(
        private http: HttpClient,
        private ui: UIService,
        private stockService:StockService,
        private financeService:FinanceService,
        public dialog: MatDialog)
    { }

    ngOnInit(): void {
        if (this.isCreation()) {
            this.pageTitle = "Création d'une Commande"
        } else {                
            this.pageTitle = "Édition du  de Commande " + this.bean.commandeCode.value
        }

        this.initSelectMagasinOptions();
        this.initSelectClientOptions();
        this.initSelectCompteOptions();
        
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
        this.bean.paiementComptant.value = this.bean.prixTotal.value;
        this.bean.paiementComptant.valueChanged = true
    }

    saveAction() {
        this.http.post(`achat-vente/commande`, BeanTools.reviewBeanAction(this.bean))
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.ui.displayFlashMessage(<Array<Message>>data.messages);
            });
    }    

    editRemiseAction(bean: CommandeBean) {
        let dialogRef = this.dialog.open(EditRemiseDialogComponent, { data: bean });
        dialogRef.afterClosed().subscribe(result => {
            this.initPrix();
        });  
    }

    /* *****************
     * Fin des actions *
     * *****************
     */
    private isCreation(): boolean {
        return EditActionEnumVd.CREATE == this.bean.action;
    }

    private calculerPrixTotalLigne(ligne: LigneBean) {
        if (ligne.prixUnitaire.value && ligne.quantite.value) {
            return ligne.prixUnitaire.value * ligne.quantite.value;
        }
        return 0
    }

    private initSelectMagasinOptions() {
        this.stockService.getMagasinsAsValueOptions(this.http, {})
            .subscribe(e => this.bean.magasin.magasinCode.valueOptions = e)
    }

    private initSelectCompteOptions() {
        this.financeService.getComptesAsValueOptions(this.http, {})
            .subscribe(e => this.bean.compte.compteCode.valueOptions = e)
    }

    private initSelectClientOptions() {
        CoreUtils.getPartnerAsValueOptions(this.http, {})
            .subscribe(e => this.bean.partner.partnerCode.valueOptions = e)
    }

    /**
     * Invoquée uniquement lorsqu'un facteur de prix a été modifié. Lors du chargement de la commande, tous les champs 
     * calculables doivent avoir été initialisés par le serveur.
     * Les facteurs de prix sont les suivants:
     * - Le montant de la remise sur la commande. Le type de la remise étant MONTANT
     * - Le taux de la remise sur la commande.
     * - Les facteurs de prix des lignes de la commande qui sont:
     *   . Le montant de la remise sur la ligne de commande. Le type de la remise étant MONTANT
     *   . Le taux de la remise sur la ligne. Le type de la remise étant TAUX
     *   . La quantité d'article commandé sur la ligne
     *   . Le prix unitaire sur la ligne
     *   . Le montanr de la taxe si applicable
     * 
     */
    private initPrix() {

        this.bean.prixTotal.value = this.bean.lignes.map(e => this.calculerPrixTotalLigne(e))
            .reduce((a, b) => a + b, 0)
    }

}
