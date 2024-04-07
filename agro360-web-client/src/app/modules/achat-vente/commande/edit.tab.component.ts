import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
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

@Component({
    standalone: true,
    imports: [
        SharedModule,
        EditLigneListComponent
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
        private financeService:FinanceService)
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
        this.initPrixTotal();
    }

    /**************************************************
     * Liste des évènements générés par l'utilisateur *
     * ************************************************
     */

    /**
     * Cette évènement est enregistré au niveau des lignes
     * du bon de commande et permet de mettre à jour le prix total 
     * du bon de commande lorsque l'on modifie la quantité ou le prix 
     * unitaire d'une ligne.
     */
    updatePrixTotalEvent() {
        this.initPrixTotal()
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

    private initPrixTotal() {
        this.bean.prixTotal.value = this.bean.lignes.map(e => this.calculerPrixTotalLigne(e))
            .reduce((a, b) => a + b, 0)
    }

}
