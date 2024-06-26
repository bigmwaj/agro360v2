
import { Component, OnInit, ViewChild, Input } from '@angular/core';
import { MatTable } from '@angular/material/table';
import { BeanList } from 'src/app/modules/common/bean/bean.list';
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

    @Input()
    productions: Array<ProductionBean>;

    displayedColumns: string[] = [
        'article',
        'variantCode',
        'quantite',
        'commentaire',
    ];

    getKeyLabel(bean: ProductionBean): number {
        return bean.productionId.value;
    }

    ngOnInit(): void {
        this.setData(this.productions)
    }

}
