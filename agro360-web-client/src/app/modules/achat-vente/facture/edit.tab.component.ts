import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { map } from 'rxjs';
import { FactureBean } from 'src/app/backed/bean.av';
import { Message } from 'src/app/backed/message';
import { EditActionEnumVd } from 'src/app/backed/vd.common';
import { BeanTools } from 'src/app/common/bean.tools';
import { UIService } from 'src/app/common/service/ui.service';
import { SharedModule } from 'src/app/common/shared.module';
import { CoreUtils } from '../../core/core.utils';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'achat-vente-facture-edit-tab',
    templateUrl: './edit.tab.component.html'
})
export class EditTabComponent implements OnInit {

    @Input({required:true})
    bean: FactureBean;

    pageTitle: string = "Edition";

    constructor(
        private http: HttpClient,
        private ui: UIService)
    { }

    ngOnInit(): void {
        if (this.isCreation()) {
            this.pageTitle = "Création d'une Facture"
        } else {
            this.pageTitle = "Édition du  de Facture " + this.bean.factureCode.value
        }
    }

    /**************************************************
     * Liste des évènements générés par l'utilisateur *
     * ************************************************
     */


    /* ********************
     * Fin des évènements *
     * ********************
     */

    /******************************************************
     * Liste des actions que peut exécuter un utilisateur *
     * ****************************************************
     */

    saveAction() {
        this.http.post(`achat-vente/facture`, BeanTools.reviewBeanAction(this.bean))
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

    private initSelectClientOptions() {
        CoreUtils.getPartnerAsValueOptions(this.http, {})
            .subscribe(e => this.bean.partner.partnerCode.valueOptions = e)
    }

}
