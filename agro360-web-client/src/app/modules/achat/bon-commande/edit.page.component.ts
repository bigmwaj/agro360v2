import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BonCommandeBean, LigneBean } from 'src/app/backed/bean.achat';
import { TiersUtils } from '../../tiers/tiers.utils';
import { StockUtils } from '../../stock/stock.utils';
import { BeanTools } from 'src/app/common/bean.tools';
import { EditLigneListComponent } from './edit.ligne.list.component';
import { SharedModule } from 'src/app/common/shared.module';
import { AchatService } from '../achat.service';
import { map } from 'rxjs';
import { Message } from 'src/app/backed/message';
import { UIService } from 'src/app/common/service/ui.service';

@Component({
    standalone: true,
    imports: [
        EditLigneListComponent,
        SharedModule
    ],
    selector: 'achat-bonCommande-edit-page',
    templateUrl: './edit.page.component.html'
})
export class EditPageComponent implements OnInit {

    bean: BonCommandeBean;

    pageTitle: string = "Edition";

    constructor(
        private route: ActivatedRoute,
        private http: HttpClient,
        private router: Router,
        private ui: UIService,
        private service:AchatService)
    { }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        if (this.isCreation()) {
            this.route.queryParamMap.subscribe(params => {
                const copyFrom = params.get('copyFrom');
                if (copyFrom) {
                    queryParams = queryParams.append("copyFrom", copyFrom);
                }
                this.http
                    .get(`achat/bon-commande/create-form`, { params: queryParams })
                    .subscribe(data => {
                        this.bean = <BonCommandeBean>data;
                        
                        this.initSelectMagasinOptions();
                        this.initSelectClientOptions();
                        this.initPrixTotal();
                    });

                this.pageTitle = "Création d'un Bon de Commande"
            });
        } else {
            // On doit traiter les potentielles erreurs
            this.route.paramMap.subscribe(params => {
                const bonCommandeCode = params.get('bonCommandeCode');
                if (!!bonCommandeCode) {
                    queryParams = queryParams.append("bonCommandeCode", bonCommandeCode);
                }
                this.http
                    .get<any>(`achat/bon-commande/edit-form`, { params: queryParams })
                    .subscribe(data => {
                        this.bean = data;
                        
                        this.initSelectMagasinOptions();
                        this.initSelectClientOptions();
                        this.initPrixTotal();
                    });

                this.pageTitle = "Édition du Bon de Commande " + bonCommandeCode
            });
        }
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
    addAction() {
        throw new Error('Méthode non implementée');
    }

    copyAction() {
        throw new Error('Méthode non implementée');
    }

    deleteAction() {
        throw new Error('Méthode non implementée');
    }

    changeStatusAction() {
        throw new Error('Méthode non implementée');
    }

    saveAction() {
        this.http.post(`achat/bon-commande`, BeanTools.reviewBeanAction(this.bean))
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.redirectToEditPage(data.id)
                this.ui.displayFlashMessage(<Array<Message>>data.messages);
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
            .subscribe(e => this.bean.fournisseur.tiersCode.valueOptions = e)
    }

    private initPrixTotal() {
        this.bean.prixTotal.value = this.bean.lignes.map(e => this.calculerPrixTotalLigne(e))
            .reduce((a, b) => a + b, 0)
    }
    
    private redirectToEditPage(id:string):void{
        this.router.navigate(
            [
                '/achat/bon-commande',
                'edit', 
                id
            ]
        )
    }

}
