import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CommandeBean, LigneBean } from 'src/app/backed/bean.vente';
import { BeanTools } from 'src/app/common/bean.tools';
import { SharedModule } from 'src/app/common/shared.module';
import { CommonUtlis } from 'src/app/common/utils/common.utils';
import { StockUtils } from '../../stock/stock.utils';
import { TiersUtils } from '../../tiers/tiers.utils';
import { EditLigneListComponent } from './edit.ligne.list.component';
import { VenteService } from '../vente.service';
import { map } from 'rxjs';
import { Message } from 'src/app/backed/message';

@Component({
    standalone: true,
    imports: [
        SharedModule,
        EditLigneListComponent
    ],
    selector: 'vente-commande-edit-page',
    templateUrl: './edit.page.component.html',
})
export class EditPageComponent implements OnInit {

    bean: CommandeBean;

    pageTitle: string = "Edition";

    constructor(
        private route: ActivatedRoute,
        private http: HttpClient,
        private router: Router,
        private service: VenteService) { }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        if (this.isCreation()) {
            this.route.queryParamMap.subscribe(params => {
                const copyFrom = params.get('copyFrom');
                if (copyFrom) {
                    queryParams = queryParams.append("copyFrom", copyFrom);
                }
                this.http
                    .get(`${CommonUtlis.BASE_URL}/vente/commande/create-form`, { params: queryParams })
                    .subscribe(data => {
                        this.bean = <CommandeBean>data;
                        this.initSelectMagasinOptions();
                        this.initSelectClientOptions();
                        this.initPrixTotal();
                    });

                this.pageTitle = "Création d'un Commande"
            });
        } else {
            // On doit traiter les potentielles erreurs
            this.route.paramMap.subscribe(params => {
                const commandeCode = params.get('commandeCode');
                if (!!commandeCode) {
                    queryParams = queryParams.append("commandeCode", commandeCode);
                }
                this.http
                    .get<any>(`${CommonUtlis.BASE_URL}/vente/commande/update-form`, { params: queryParams })
                    .subscribe(data => {
                        this.bean = data;
                        this.initSelectMagasinOptions();
                        this.initSelectClientOptions();
                        this.initPrixTotal();
                    });

                this.pageTitle = "Édition du Commande " + commandeCode
            });
        }
    }

    /**************************************************
     * Liste des évènements générés par l'utilisateur *
     * ************************************************
     */

    /**
     * Cette évènement est enregistré au niveau des lignes
     * de la commande et permet de mettre à jour le prix total 
     * la commande lorsque l'on modifie la quantité ou le prix 
     * unitaire d'une ligne
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

    /**
     * Action ménant à la création d'une nouvelle commande
     */
    addAction() {
        throw new Error('Méthode non implementée');
    }

    /**
     * Action ménant à la copie de la commande en cours d'édition
     */
    copyAction() {
        throw new Error('Méthode non implementée');
    }
    
    /**
     * Action ménant à la copie de la commande en cours d'édition
     */
    changeAction() {
        throw new Error('Méthode non implementée');
    }

    /**
     * Action ménant à l'enregistrement de la commande en cours de création/modification
     */
    saveAction() {
        this.http.post(`${CommonUtlis.BASE_URL}/vente/commande`, BeanTools.reviewBeanAction(this.bean))
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.redirectToEditPage(data.id)
                this.service.displayFlashMessage(<Array<Message>>data.messages);
            });
    }

    /* *****************
     * Fin des actions *
     * *****************
     */

    private isCreation(): boolean {
        let path = this.route.routeConfig?.path;
        return !!path && path.endsWith("create");
    }

    private calculerPrixTotalLigne(ligne: LigneBean) {
        if (ligne.prixUnitaire.value && ligne.quantite.value) {
            return ligne.prixUnitaire.value * ligne.quantite.value;
        }
        return 0
    }

    private initSelectMagasinOptions() {
        StockUtils.getMagasinsAsValueOptions(this.http, {})
            .subscribe(e => this.bean.magasin.magasinCode.valueOptions = e)
    }

    private initSelectClientOptions() {
        TiersUtils.getTiersAsValueOptions(this.http, {})
            .subscribe(e => this.bean.client.tiersCode.valueOptions = e)
    }

    private initPrixTotal() {
        this.bean.prixTotal.value = this.bean.lignes.map(e => this.calculerPrixTotalLigne(e))
            .reduce((a, b) => a + b, 0)
    }

    private redirectToEditPage(id:string):void{
        this.router.navigate(
            [
                '/vente/commande',
                'edit', 
                id
            ]
        )
    }
}
