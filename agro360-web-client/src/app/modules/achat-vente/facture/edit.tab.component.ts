import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { map } from 'rxjs';
import { FactureBean } from 'src/app/backed/bean.av';
import { Message } from 'src/app/backed/message';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';
import { BeanTools } from 'src/app/modules/common/bean.tools';
import { BreadcrumbItem, UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { IBeanEditTab } from '../../common/bean.edit.tab';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'achat-vente-facture-edit-tab',
    templateUrl: './edit.tab.component.html'
})
export class EditTabComponent implements OnInit, IBeanEditTab {
    
    @Input({required:true})
    breadcrumb:BreadcrumbItem

    @Input({required:true})
    bean: FactureBean;

    @Input({required:true})
    module:string;

    partnerLabel: string;

    constructor(
        private http: HttpClient,
        private ui: UIService)
    { }
        
    ngAfterViewInit(): void {
        this.refreshPageTitle();
        switch(this.module){
            case 'vente': this.partnerLabel = 'Client'; break;
            case 'achat': this.partnerLabel = 'Fournisseur'; break;
            default:
                throw new Error(`Aucun type de partenaire n'a été configuré pour le module ${this.module}`)
        }
    }

    refreshPageTitle():void{
        this.ui.setBreadcrumb(this.breadcrumb)
    }

    ngOnInit(): void {
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
