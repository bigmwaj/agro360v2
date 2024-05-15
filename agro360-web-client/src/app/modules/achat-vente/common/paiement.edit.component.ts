import { Component, Input } from '@angular/core';
import { CommandeBean, FactureBean, PaiementBean } from 'src/app/backed/bean.av';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanList } from '../../common/bean/bean.list';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'achat-vente-common-paiement-edit',
    templateUrl: './paiement.edit.component.html',
})
export class PaiementEditComponent  extends BeanList<PaiementBean> {

    @Input({required:true})
    paiements: Array<PaiementBean>;
    
    @Input({required:true})
    bean: CommandeBean | FactureBean;

    displayedColumns: string[] = [
        'compte',
        'montant'
    ];

    constructor() {
        super()
    }

    override getKeyLabel(bean: PaiementBean): string | number {
        return bean.compte.compteCode.value;
    }

    ngOnInit(): void {
        this.setData(this.paiements)
    }

    releverResteAction(paiement: PaiementBean){
        let reste = this.bean.prixTotal.value - this.bean.cumulPaiement.value;

        let montant = reste - this.paiements
            .filter(e => e != paiement).map(e => e.montant.value)
            .filter( e => e > 0)
            .reduce( (a, b) => a+b, 0);

        paiement.montant.value = montant
    }
    
}
