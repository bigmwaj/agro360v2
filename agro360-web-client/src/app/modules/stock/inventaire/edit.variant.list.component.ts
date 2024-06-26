import { Component, OnInit } from '@angular/core';
import { InventaireBean } from 'src/app/backed/bean.stock';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';
import { BeanList } from 'src/app/modules/common/bean/bean.list';
import { SharedModule } from 'src/app/modules/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'stock-inventaire-edit-variant-list',
    templateUrl: './edit.variant.list.component.html'
})
export class EditVariantListComponent extends BeanList<InventaireBean> implements OnInit {

    displayedColumns: string[] = [
        'select',
        'variantCode',
        'alias',
        'unite.stock',
        'unite.achat',
        'unite.vente',
        'description',
    ];

    constructor() {
        super()
    }

    getKeyLabel(bean: InventaireBean): string {
        return bean.variantCode.value;
    }

    ngOnInit(): void {
        
    }

    toggleChange(bean:InventaireBean){
        if( this.selection.isSelected(bean) ){
            bean.action = ClientOperationEnumVd.CREATE;
        }else{
            bean.action = ClientOperationEnumVd.SYNC;
        }
    }

    override toggleAllRows(){
        super.toggleAllRows();

        this.data.forEach(e => this.toggleChange(e));
    }
}
