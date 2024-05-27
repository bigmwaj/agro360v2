import { Component, Input } from '@angular/core';
import { CommandeBean, FactureBean, PaiementParamBean } from 'src/app/backed/bean.av';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { MatDividerModule } from '@angular/material/divider';
import { MatCardModule } from '@angular/material/card';

@Component({
    standalone: true,
    imports: [
        SharedModule,
        MatDividerModule,
        MatCardModule
    ],
    selector: 'achat-vente-common-paiement-edit',
    templateUrl: './paiement.edit.component.html',
})
export class PaiementEditComponent  {

    @Input({required:true})
    paiements: Array<PaiementParamBean>;
    
    @Input({required:true})
    bean: CommandeBean | FactureBean;

    releverResteAction(paiement: PaiementParamBean){
        let reste = this.bean.prixTotal.value - this.bean.cumulPaiement.value;

        let montant = reste - this.paiements
            .filter(e => e != paiement).map(e => e.montant.value)
            .filter( e => e > 0)
            .reduce( (a, b) => a+b, 0);

        paiement.montant.value = montant
    }
    
}
