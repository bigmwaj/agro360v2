
import { Component, Input, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { EditTabComponent } from './edit.tab.component';
import { MatTabGroup, MatTabsModule } from '@angular/material/tabs';
import { ListTabComponent } from './list.tab.component';
import { MagasinBean } from 'src/app/backed/bean.stock';
import { CommonModule } from '@angular/common';
import { BreadcrumbItem } from 'src/app/modules/common/service/ui.service';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';

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
export class IndexPageComponent implements OnInit {

    editingBeans: MagasinBean[] = [];

    selectedTab: {index:number} = {index:0}

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

    refreshPageTitle():void{
        var selectedIndex = this.tabGroup.selectedIndex
        if( selectedIndex == null ){
            selectedIndex = 0
        }
        this._refreshTabTitle(selectedIndex)
    }

    selectedTabChange($event:any):void{
        this._refreshTabTitle($event.index);
        this.selectedTab.index = $event.index;
    }

    private _refreshTabTitle(index: number){
        if( index == 0 ){
            this.listTab.refreshPageTitle()
        }else{
            let editTab = this.editTabs.get(index-1);
            editTab?.refreshPageTitle()
        }
    }

    private getEditingBeanIndex(bean:MagasinBean):number{
        let filter = (e:MagasinBean) => (ClientOperationEnumVd.CREATE == bean.action && e == bean) 
            || e.magasinCode.value == bean.magasinCode.value;
        return this.editingBeans.findIndex(filter);
    }

    removeAction(bean:MagasinBean){
        const selectedTabIndex = this.selectedTab.index;
        const editingBeanIndex = this.getEditingBeanIndex(bean);
        this.editingBeans = this.editingBeans.filter((e, i)=> i != editingBeanIndex);
        if( selectedTabIndex == editingBeanIndex + 1 ){
            this.selectedTab.index = editingBeanIndex //On recule
        }
    }

}
