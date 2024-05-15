
import { CommonModule } from '@angular/common';
import { Component, Input, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatTabGroup, MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MagasinBean } from 'src/app/backed/bean.stock';
import { BreadcrumbItem } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanIndexPage } from '../../common/bean/bean.index.page';
import { EditTabComponent } from './edit.tab.component';
import { ListTabComponent } from './list.tab.component';

@Component({
    standalone: true,
    imports: [
        EditTabComponent,        
        CommonModule,        
        MatToolbarModule, 
        SharedModule,
        MatIconModule,
        MatTabsModule,
        ListTabComponent
    ],
    selector: 'stock-magasin-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent extends BeanIndexPage<MagasinBean, ListTabComponent, EditTabComponent> implements OnInit {

    @Input()
    breadcrumb:BreadcrumbItem;
    
    @ViewChild('stock_magasin')
    tabGroup: MatTabGroup;
    
    @ViewChildren(EditTabComponent) 
    editTabs!: QueryList<EditTabComponent>;
    
    @ViewChild(ListTabComponent) 
    listTab: ListTabComponent;

    ngOnInit(): void {
        if( !this.breadcrumb ){
            this.breadcrumb = new BreadcrumbItem('Magasins')
        }else{
            this.breadcrumb = this.breadcrumb.addAndReturnChildItem('Magasins');
        }
    }

    protected areBeansEqual(b1: MagasinBean, b2: MagasinBean):boolean{
        return b1 == b2 || b1.magasinCode.value == b2.magasinCode.value;
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
