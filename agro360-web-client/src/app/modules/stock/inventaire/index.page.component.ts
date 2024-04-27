
import { Component, Input, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { EditTabComponent } from './edit.tab.component';
import { MatTabGroup, MatTabsModule } from '@angular/material/tabs';
import { ListTabComponent } from './list.tab.component';
import { InventaireBean } from 'src/app/backed/bean.stock';
import { BreadcrumbItem } from 'src/app/modules/common/service/ui.service';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';
import { BeanIndexPage } from '../../common/bean.index.page';

@Component({
    standalone: true,
    imports: [
        EditTabComponent,
        SharedModule,        
        MatToolbarModule, 
        MatIconModule,
        MatTabsModule,
        ListTabComponent
    ],
    selector: 'stock-inventaire-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent  extends BeanIndexPage<InventaireBean, ListTabComponent, EditTabComponent> implements OnInit {

    ngOnInit(): void {
        if( !this.breadcrumb ){
            this.breadcrumb = new BreadcrumbItem('Inventaires')
        }else{
            this.breadcrumb = this.breadcrumb.addAndReturnChildItem('Invetaires');
        }
    }
    
    @Input()
    breadcrumb:BreadcrumbItem;
    
    @ViewChild('stock_inventaire')
    tabGroup: MatTabGroup;
    
    @ViewChildren(EditTabComponent) 
    editTabs!: QueryList<EditTabComponent>;
    
    @ViewChild(ListTabComponent) 
    listTab: ListTabComponent;

    protected areBeansEqual(b1: InventaireBean, b2: InventaireBean):boolean{
        return b1 == b2 || (
            (   b1.magasin.magasinCode.value == b2.magasin.magasinCode.value)
            && (b1.article.articleCode.value == b2.article.articleCode.value)
            && (b1.variantCode.value == b2.variantCode.value)
        );
    }

    protected override getEditTabs(): QueryList<EditTabComponent> {
        return this.editTabs;
    }

    protected override getTabGroup(): MatTabGroup {
        return this.tabGroup;
    }

    protected override getListTab(): ListTabComponent {
        return this.listTab;
    }

}
