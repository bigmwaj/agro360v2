import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { TransactionBean } from 'src/app/backed/bean.finance';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanTools } from 'src/app/modules/common/bean.tools';
import { Message } from 'src/app/backed/message';
import { BreadcrumbItem, UIService } from 'src/app/modules/common/service/ui.service';
import { map } from 'rxjs';
import { TransactionTypeEnumVd } from 'src/app/backed/vd.finance';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';
import { IBeanEditTab } from '../../common/bean.edit.tab';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'finance-transaction-edit-tab',
    templateUrl: './edit.tab.component.html'
})
export class EditTabComponent implements OnInit, IBeanEditTab {
    @Input({required:true})
    breadcrumb:BreadcrumbItem
    
    @Input({required:true})
    bean: TransactionBean;
    
    @Input({required:true})
    module: string;
    

    partnerLabel: string = "Partenaire";

    constructor( 
        private http: HttpClient,
        private ui: UIService) { }

    private initModule(): void{

        let isCreation = this.isCreation();

        let title = "Création d'une transaction";
        if( this.module != null ){
            this.bean.rubrique.rubriqueCode.editable = false
            this.bean.type.editable = false

            let rubriqueCode = ''
            let transactionType:TransactionTypeEnumVd|undefined = undefined

            switch(this.module){
                case "vente": 
                transactionType = TransactionTypeEnumVd.RECETTE
                rubriqueCode = 'VENTE'
                this.partnerLabel = "Client";

                if( isCreation )
                    title = `Création d'un encaissement client`;
                else            
                    title = `Edition de l'encaissement client ${this.bean.transactionCode.value}`;
                break;

                case "achat": 
                transactionType = TransactionTypeEnumVd.DEPENSE;
                rubriqueCode = 'ACHAT';
                this.partnerLabel = "Fournisseur";

                if( isCreation )
                    title = `Création d'un règlement fournisseur`;
                else            
                    title = `Edition du règlement fournisseur ${this.bean.transactionCode.value}`;
                break;
                break;

                case "paie": 
                transactionType = TransactionTypeEnumVd.DEPENSE;
                rubriqueCode = 'SALAIRE';
                this.partnerLabel = "Employé";
                title = "Création d'un salaire employé";

                if( isCreation )
                    title = `Création d'un salaire employé`;
                else            
                    title = `Edition du salaire employé ${this.bean.transactionCode.value}`;
                break;
            }   
            
            if( isCreation ){
                if( transactionType != undefined){
                    this.bean.type.value = transactionType
                }
                this.bean.rubrique.rubriqueCode.value = rubriqueCode
            }
        }
        this.breadcrumb = this.breadcrumb.addAndReturnChildItem(title)
    }

    ngOnInit(): void {
        this.initModule()
    }

    ngAfterViewInit(): void {
        this.refreshPageTitle()
    }

    refreshPageTitle():void{
        this.ui.setBreadcrumb(this.breadcrumb)
    }
    
    saveAction() {
        this.http.post(`finance/transaction`, BeanTools.reviewBeanAction(this.bean))   
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.ui.displayFlashMessage(<Array<Message>>data.messages);
            });
    }

    private isCreation(): boolean {
        return ClientOperationEnumVd.CREATE == this.bean.action;
    }

}
