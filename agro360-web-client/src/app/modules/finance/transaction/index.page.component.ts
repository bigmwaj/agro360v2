
import { Component, Input, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { TransactionBean } from 'src/app/backed/bean.finance';
import { IndexModalComponent as RubriqueIndexModalComponent } from '../rubrique/index.modal.component';
import { IndexModalComponent as CompteIndexModalComponent } from '../compte/index.modal.component';
import { SharedModule } from 'src/app/common/shared.module';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { EditTabComponent } from './edit.tab.component';
import { MatTabGroup, MatTabsModule } from '@angular/material/tabs';
import { ListTabComponent } from './list.tab.component';
import { BreadcrumbItem } from 'src/app/common/service/ui.service';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';

@Component({
    standalone: true,
    imports: [
        RubriqueIndexModalComponent,
        CompteIndexModalComponent,
        EditTabComponent,
        SharedModule,        
        MatToolbarModule, 
        MatIconModule,
        MatTabsModule,
        ListTabComponent
    ],
    selector: 'finance-transaction-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent implements OnInit {   

    @ViewChild('finance_transaction')
    tabGroup: MatTabGroup;
    
    @ViewChildren(EditTabComponent) 
    editTabs!: QueryList<EditTabComponent>;
    
    @ViewChild(ListTabComponent) 
    listTab: ListTabComponent;

    editingBeans: TransactionBean[] = [];

    selectedTab: {index:number} = {index:0}

    /**
     * Les différents modules qui peuvent faire appelle à cette fonctionnalité
     * vente
     * achat
     * paie
     */
    @Input()
    module:string

    @Input()
    breadcrumb:BreadcrumbItem;
    
    ngOnInit(): void {
        if( this.breadcrumb == undefined ){
            this.breadcrumb = new BreadcrumbItem('Finances générales');
        }else{
            if( this.module ){
                switch(this.module){
                    case 'vente': 
                        this.breadcrumb = this.breadcrumb.addAndReturnChildItem('Recettes');
                        break;

                    case 'achat': 
                        this.breadcrumb = this.breadcrumb.addAndReturnChildItem('Dépenses');
                        break;

                    case 'paie': 
                        this.breadcrumb = this.breadcrumb.addAndReturnChildItem('Salaires');
                        break;

                    default:                         
                        this.breadcrumb = this.breadcrumb.addAndReturnChildItem(`Module ${this.module} inconnu!'`);
                        break;
                }
            }
        }        
    }

    private getEditingBeanIndex(bean:TransactionBean):number{
        let filter = (e:TransactionBean) => (ClientOperationEnumVd.CREATE == bean.action && e == bean) 
            || e.transactionCode.value == bean.transactionCode.value;
        return this.editingBeans.findIndex(filter);
    }

    removeAction(bean:TransactionBean){
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
