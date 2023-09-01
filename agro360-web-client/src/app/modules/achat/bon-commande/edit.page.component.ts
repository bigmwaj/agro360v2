import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { BonCommandeBean, LigneBean } from 'src/app/backed/bean.achat';
import { TiersUtils } from '../../tiers/tiers.utils';
import { StockUtils } from '../../stock/stock.utils';
import { CommonUtlis } from 'src/app/common/utils/common.utils';
import { BeanTools } from 'src/app/common/bean.tools';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { EditLigneListComponent } from './edit.ligne.list.component';
import { SharedModule } from 'src/app/common/shared.module';

@Component({
    standalone: true,
    imports: [
        CommonModule,
        MatButtonModule,
        MatIconModule,
        MatDialogModule,
        MatCheckboxModule,
        MatTableModule,
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
        private http: HttpClient) { }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        if (this.isCreation()) {
            this.route.queryParamMap.subscribe(params => {
                const copyFrom = params.get('copyFrom');
                if (copyFrom) {
                    queryParams = queryParams.append("copyFrom", copyFrom);
                }
                this.http
                    .get(`${CommonUtlis.BASE_URL}/achat/bon-commande/create-form`, { params: queryParams })
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
                    .get<any>(`${CommonUtlis.BASE_URL}/achat/bon-commande/update-form`, { params: queryParams })
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
        this.http.post(`${CommonUtlis.BASE_URL}/achat/bon-commande`, BeanTools.reviewBeanAction(this.bean))
        .subscribe(data => console.log(data))
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

}
