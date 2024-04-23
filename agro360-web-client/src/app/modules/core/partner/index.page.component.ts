
import { Component, Input, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatTabGroup, MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { PartnerBean } from 'src/app/backed/bean.core';
import { SharedModule } from 'src/app/common/shared.module';
import { EditTabComponent } from './edit.tab.component';
import { ListTabComponent } from './list.tab.component';
import { BreadcrumbItem } from 'src/app/common/service/ui.service';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';

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
    selector: 'core-partner-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent implements OnInit {

    @Input()
    module:string;

    @ViewChild('core_partenaire')
    tabGroup: MatTabGroup;
    
    @ViewChildren(EditTabComponent) 
    editTabs!: QueryList<EditTabComponent>;
    
    @ViewChild(ListTabComponent) 
    listTab: ListTabComponent;

    editingBeans: PartnerBean[] = [];

    selectedTab: {index:number} = {index:0}
    
    @Input()
    breadcrumb:BreadcrumbItem

    ngOnInit(): void {
        if( !this.breadcrumb ){
            this.breadcrumb = new BreadcrumbItem('Partenaires')
        }else{
            switch(this.module){
                case 'vente': 
                    this.breadcrumb = this.breadcrumb.addAndReturnChildItem('Clients');
                    break;

                case 'achat': 
                    this.breadcrumb = this.breadcrumb.addAndReturnChildItem('Fournisseurs');
                    break;

                case 'paie': 
                    this.breadcrumb = this.breadcrumb.addAndReturnChildItem('Employés');
                    break;

                default:                         
                    this.breadcrumb = this.breadcrumb.addAndReturnChildItem(`Module ${this.module} inconnu!'`);
                    break;
            }
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

    private getEditingBeanIndex(bean:PartnerBean):number{
        let filter = (e:PartnerBean) => (ClientOperationEnumVd.CREATE == bean.action && e == bean) 
            || e.partnerCode.value == bean.partnerCode.value;
        return this.editingBeans.findIndex(filter);
    }

    removeAction(bean:PartnerBean){
        const selectedTabIndex = this.selectedTab.index;
        const editingBeanIndex = this.getEditingBeanIndex(bean);
        this.editingBeans = this.editingBeans.filter((e, i)=> i != editingBeanIndex);
        if( selectedTabIndex == editingBeanIndex + 1 ){
            this.selectedTab.index = editingBeanIndex //On recule
        }
    }

    private _refreshTabTitle(index: number){
        if( index == 0 ){
            this.listTab.refreshPageTitle()
        }else{
            let editTab = this.editTabs.get(index-1);
            editTab?.refreshPageTitle()
        }
    }
}
