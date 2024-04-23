import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatTable } from '@angular/material/table';
import { VariantBean } from 'src/app/backed/bean.stock';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';
import { BeanList } from 'src/app/common/component/bean.list';
import { SharedModule } from 'src/app/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'stock-inventaire-edit-variant-list',
    templateUrl: './edit.variant.list.component.html'
})
export class EditVariantListComponent extends BeanList<VariantBean> implements OnInit {

    @ViewChild(MatTable)
    table: MatTable<VariantBean>;

    displayedColumns: string[] = [
        'select',
        'variantCode',
        'alias',
        'description',
    ];

    constructor() {
        super()
    }

    override getViewChild(): MatTable<VariantBean> {
        return this.table;
    }

    getKeyLabel(bean: VariantBean): string {
        return bean.variantCode.value;
    }

    ngOnInit(): void {
        
    }

    toggleChange(bean:VariantBean){
        if( this.selection.isSelected(bean) ){
            bean.action = ClientOperationEnumVd.CREATE;
        }else{
            bean.action = ClientOperationEnumVd.SYNC;
        }

        console.log( bean)
    }

    override toggleAllRows(){
        super.toggleAllRows();

        this.data.forEach(e => this.toggleChange(e));
    }
}
