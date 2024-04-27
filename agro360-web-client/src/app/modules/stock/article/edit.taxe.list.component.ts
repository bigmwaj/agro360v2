import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatTable } from '@angular/material/table';
import { ArticleTaxeBean } from 'src/app/backed/bean.stock';
import { BeanList } from 'src/app/modules/common/bean.list';
import { SharedModule } from 'src/app/modules/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'stock-article-edit-taxe-list',
    templateUrl: './edit.taxe.list.component.html'
})
export class EditTaxeListComponent extends BeanList<ArticleTaxeBean> implements OnInit {

    getKeyLabel(bean: ArticleTaxeBean): string {
        return bean.taxe.taxeCode.value;
    }

    @Input()
    articleCode: string;

    @Input()
    taxes: Array<ArticleTaxeBean>;

    displayedColumns: string[] = [
        'select',
        'taxe',
        'taux',
        'description'
    ];

    constructor() {
        super()
    }

    ngOnInit(): void {
        this.setData(this.taxes)
        this.data.filter(e => e.selected.value).forEach(e => this.selection.select(e))
    }

    toggleChange(bean:ArticleTaxeBean){
        bean.selected.value = this.selection.isSelected(bean);
        bean.selected.valueChanged = true;
    }

    override toggleAllRows(){
        super.toggleAllRows();

        this.taxes.forEach(e => this.toggleChange(e));
    }
}
