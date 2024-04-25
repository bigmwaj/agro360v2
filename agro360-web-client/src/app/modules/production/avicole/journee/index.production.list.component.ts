
import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { MatTable } from '@angular/material/table';
import { BeanList } from 'src/app/modules/common/bean.list';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { ProductionBean } from 'src/app/backed/bean.production.avicole';
import { MatTabsModule } from '@angular/material/tabs';

@Component({
    standalone: true,
    imports: [
        SharedModule,
        MatTabsModule
    ],
    selector: 'production-avicole-journee-production-list',
    templateUrl: './index.production.list.component.html'
})
export class IndexProducitonListComponent extends BeanList<ProductionBean> implements OnInit {

    @ViewChild('productionsTable')
    table: MatTable<ProductionBean>;

    @Input()
    productions: Array<ProductionBean>;

    displayedColumns: string[] = [
        'article',
        'variantCode',
        'quantite',
        'commentaire',
    ];

    override getViewChild(): MatTable<ProductionBean> {
        return this.table;
    }

    getKeyLabel(bean: ProductionBean): number {
        return bean.productionId.value;
    }

    ngOnInit(): void {
        this.setData(this.productions)
    }

}
