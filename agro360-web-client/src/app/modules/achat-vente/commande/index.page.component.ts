import { CommonModule } from '@angular/common';
import { Component, Input, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { MatTabGroup, MatTabsModule } from '@angular/material/tabs';
import { CommandeBean } from 'src/app/backed/bean.av';
import { BreadcrumbItem } from 'src/app/common/service/ui.service';
import { SharedModule } from 'src/app/common/shared.module';
import { EditTabComponent } from './edit.tab.component';
import { ListTabComponent } from './list.tab.component';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';

@Component({
    standalone: true,
    imports: [
        CommonModule,
        SharedModule,
        MatTabsModule,
        ListTabComponent,
        EditTabComponent
    ],
    selector: 'achat-vente-commande-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent implements OnInit {
    
    @Input({required:true})
    module:string;
    
    @Input()
    breadcrumb:BreadcrumbItem;
    
    @ViewChild('achat_vente_commande')
    tabGroup: MatTabGroup;
    
    @ViewChildren(EditTabComponent) 
    editTabs!: QueryList<EditTabComponent>;
    
    @ViewChild(ListTabComponent) 
    listTab: ListTabComponent;

    editingBeans: CommandeBean[] = [];

    selectedTab: {index:number} = {index:0}

    ngOnInit(): void {
        if( !this.breadcrumb ){
            this.breadcrumb = new BreadcrumbItem('Achats & Ventes')
        }else{
            switch(this.module){
                case 'vente': 
                    this.breadcrumb = this.breadcrumb.addAndReturnChildItem('Commandes');
                    break;

                case 'achat': 
                    this.breadcrumb = this.breadcrumb.addAndReturnChildItem('Commandes');
                    break;

                default:                         
                    this.breadcrumb = this.breadcrumb.addAndReturnChildItem(`Module ${this.module} inconnu!'`);
                    break;
            }
        }
    }

    private getEditingBeanIndex(bean:CommandeBean):number{
        let filter = (e:CommandeBean) => (ClientOperationEnumVd.CREATE == bean.action && e == bean) 
            || e.commandeCode.value == bean.commandeCode.value;
        return this.editingBeans.findIndex(filter);
    }

    removeAction(bean:CommandeBean){
        const selectedTabIndex = this.selectedTab.index;
        const editingBeanIndex = this.getEditingBeanIndex(bean);
        this.editingBeans = this.editingBeans.filter((e, i)=> i != editingBeanIndex);
        if( selectedTabIndex == editingBeanIndex + 1 ){
            this.selectedTab.index = editingBeanIndex //On recule
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
}
