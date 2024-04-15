import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { TransactionBean } from 'src/app/backed/bean.finance';
import { SharedModule } from 'src/app/common/shared.module';
import { BeanTools } from 'src/app/common/bean.tools';
import { Message } from 'src/app/backed/message';
import { UIService } from 'src/app/common/service/ui.service';
import { map } from 'rxjs';
import { TransactionTypeEnumVd } from 'src/app/backed/vd.finance';
import { EditActionEnumVd } from 'src/app/backed/vd.common';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'finance-transaction-edit-tab',
    templateUrl: './edit.tab.component.html'
})
export class EditTabComponent implements OnInit {

    @Input({required:true})
    bean: TransactionBean;
    
    @Input({required:true})
    module: string;
    
    title: string = "Création d'une transaction";

    partnerLabel: string = "Partenaire";

    constructor( 
        private http: HttpClient,
        private ui: UIService) { }

    private initModule(): void{

        let isCreation = this.isCreation();

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
                    this.title = `Création d'un encaissement client`;
                else            
                    this.title = `Edition de l'encaissement client ${this.bean.transactionCode.value}`;
                break;

                case "achat": 
                transactionType = TransactionTypeEnumVd.DEPENSE;
                rubriqueCode = 'ACHAT';
                this.partnerLabel = "Fournisseur";

                if( isCreation )
                    this.title = `Création d'un règlement fournisseur`;
                else            
                    this.title = `Edition du règlement fournisseur ${this.bean.transactionCode.value}`;
                break;
                break;

                case "paie": 
                transactionType = TransactionTypeEnumVd.DEPENSE;
                rubriqueCode = 'SALAIRE';
                this.partnerLabel = "Employé";
                this.title = "Création d'un salaire employé";

                if( isCreation )
                    this.title = `Création d'un salaire employé`;
                else            
                    this.title = `Edition du salaire employé ${this.bean.transactionCode.value}`;
                break;
            }   
            
            if( isCreation ){
                if( transactionType != undefined){
                    this.bean.type.value = transactionType
                }
                this.bean.rubrique.rubriqueCode.value = rubriqueCode
            }
        }
    }

    ngOnInit(): void {
        this.initModule()
        this.ui.setTitle(this.title)
    }

    saveAction() {
        this.http.post(`finance/transaction`, BeanTools.reviewBeanAction(this.bean))   
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.ui.displayFlashMessage(<Array<Message>>data.messages);
            });
    }

    private isCreation(): boolean {
        return EditActionEnumVd.CREATE == this.bean.action;
    }

}
