import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BeanTools } from 'src/app/common/bean.tools';
import { CoreUtils } from '../../core/core.utils';
import { CaisseBean, CaisseIdBean } from 'src/app/backed/bean.stock';
import { StockService } from '../stock.service';
import { OperationTypeEnumVd } from 'src/app/backed/vd.stock';
import { EditActionEnumVd } from 'src/app/backed/vd.common';
import { map } from 'rxjs';
import { SharedModule } from 'src/app/common/shared.module';
import { EditOperationListComponent } from './edit.operation.list.component';
import { UIService } from 'src/app/common/service/ui.service';

@Component({
    standalone: true,
    imports: [
        EditOperationListComponent,
        SharedModule
    ],
    selector: 'stock-caisse-edit-page',
    templateUrl: './edit.page.component.html',
})
export class EditPageComponent implements OnInit {

    bean: CaisseBean;

    pageTitle: string = "Edition";

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private http: HttpClient,
        private service: StockService,
        private ui: UIService) { }

    ngOnInit(): void {
        if (this.isCreation()) {
            this.http
                .get(`stock/caisse/create-form`)
                .subscribe(data => {
                    this.bean = <CaisseBean>data;
                    this.initSelectMagasinOptions();
                    this.initSelectClientOptions();
                    this.initSolde();
                });

            this.pageTitle = "Création d'une caisse"
        } else {
            // On doit traiter les potentielles erreurs
            const prms: any = {};
            this.route.paramMap.subscribe(params => {
                prms.magasin = params.get('magasin');
                prms.Partner = params.get('Partner');
                prms.journee = params.get('journee');
                this.http
                    .get<any>(`stock/caisse/edit-form`, { params: this.service.encodeQuery(prms) })
                    .subscribe(data => {
                        this.bean = data;
                        this.initSelectMagasinOptions();
                        this.initSelectClientOptions();
                        this.initSolde();
                    });

                this.pageTitle = "Édition de la journée " + prms.journee
            });
        }
    }

    getEncodedOwnerId(bean: CaisseBean): any {
        const query = this.service.encodeQuery({
            magasin: bean.magasin.magasinCode.value,
            Partner: bean.Partner.partnerCode.value,
            journee: bean.journee.value
        });

        return { q: query.get('q') };
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
        this.initSolde()
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
        this.router.navigate(['stock/caisse', 'create'], { replaceUrl: true });
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
        this.http.post(`stock/caisse`, BeanTools.reviewBeanAction(this.bean))
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.ui.displayFlashMessage(data.messages);
                this.redirectToEditPage(data.id);
            });
    }

    private redirectToEditPage(id:CaisseIdBean):void{
        this.router.navigate(
            [
                '/stock/caisse',
                'edit', 
                id.magasin, 
                id.Partner, 
                id.journee
            ]
        )
    }

    /* *****************
     * Fin des actions *
     * *****************
     */

    isCreation(): boolean {
        let path = this.route.routeConfig?.path;
        return !!path && path.endsWith("create");
    }

    private initSelectMagasinOptions() {
        this.service.getMagasinsAsValueOptions(this.http, {})
            .subscribe(e => this.bean.magasin.magasinCode.valueOptions = e)
    }

    private initSelectClientOptions() {
        CoreUtils.getPartnerAsValueOptions(this.http, {})
            .subscribe(e => this.bean.Partner.partnerCode.valueOptions = e)
    }

    private calculerTotalOperation(ope: OperationTypeEnumVd): number {
        return this.bean.operations
            .filter(e => e.typeOperation.value == ope)
            .filter(e => e.prixUnitaire.value != null)
            .filter(e => e.quantite.value != null)
            .filter(e => e.action != EditActionEnumVd.DELETE)
            .map(e => e.prixUnitaire.value * e.quantite.value)
            .reduce((a, b) => a + b, 0);
    }

    private initSolde() {
        this.bean.depense.value = this.calculerTotalOperation(OperationTypeEnumVd.DEPENSE);
        this.bean.recette.value = this.calculerTotalOperation(OperationTypeEnumVd.RECETTE);
        this.bean.solde.value = this.bean.recette.value - this.bean.depense.value;
    }
}
