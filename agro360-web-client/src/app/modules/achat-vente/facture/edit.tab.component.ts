import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { map } from 'rxjs';
import { FactureBean } from 'src/app/backed/bean.av';
import { Message } from 'src/app/backed/message';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';
import { BeanTools } from 'src/app/modules/common/bean.tools';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanEditTab } from '../../common/bean.edit.tab';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'achat-vente-facture-edit-tab',
    templateUrl: './edit.tab.component.html'
})
export class EditTabComponent extends BeanEditTab<FactureBean> implements OnInit {
    
    @Input({required:true})
    module:string;

    partnerLabel: string;

    constructor(
        private http: HttpClient,
        public override ui: UIService){ 
        super(ui);
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
                this.afterSaveAction(data.record)
            });
    }

    /* *****************
     * Fin des actions *
     * *****************
     */

    private isCreation(): boolean {
        return ClientOperationEnumVd.CREATE == this.bean.action;
    }

}
