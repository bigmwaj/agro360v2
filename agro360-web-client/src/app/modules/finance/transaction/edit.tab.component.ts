import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { TransactionBean } from 'src/app/backed/bean.finance';
import { TransactionTypeEnumVd } from 'src/app/backed/vd.finance';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanEditTab } from '../../common/bean.edit.tab';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'finance-transaction-edit-tab',
    templateUrl: './edit.tab.component.html'
})
export class EditTabComponent extends BeanEditTab<TransactionBean> implements OnInit {
    
    @Input({required:true})
    module: string;    

    partnerLabel: string = "Partenaire";

    constructor( 
        public override http: HttpClient,
        public override ui: UIService) {             
        super(ui, http);        
    }

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
    
    saveUrl():string {
        return `finance/transaction`;
    }

}
