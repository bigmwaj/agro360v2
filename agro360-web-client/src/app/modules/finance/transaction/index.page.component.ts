
import { Component, Input, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatTabGroup, MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { TransactionBean } from 'src/app/backed/bean.finance';
import { BreadcrumbItem } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanIndexPage } from '../../common/bean.index.page';
import { IndexModalComponent as CompteIndexModalComponent } from '../compte/index.modal.component';
import { IndexModalComponent as RubriqueIndexModalComponent } from '../rubrique/index.modal.component';
import { EditTabComponent } from './edit.tab.component';
import { ListTabComponent } from './list.tab.component';
import { TransactionTypeEnumVd } from 'src/app/backed/vd.finance';

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
export class IndexPageComponent extends BeanIndexPage<TransactionBean, ListTabComponent, EditTabComponent> implements OnInit {   

    addOptions:Array<{type:TransactionTypeEnumVd; label:string}> = [
        { type:TransactionTypeEnumVd.DEPENSE, label: 'Dépense'},
        { type:TransactionTypeEnumVd.RECETTE, label: 'Recette'},
        { type:TransactionTypeEnumVd.DEPOT, label: 'Dépot'},
        { type:TransactionTypeEnumVd.RETRAIT, label: 'Retrait'},
    ]

    @ViewChild('finance_transaction')
    tabGroup: MatTabGroup;
    
    @ViewChildren(EditTabComponent) 
    editTabs!: QueryList<EditTabComponent>;
    
    @ViewChild(ListTabComponent) 
    listTab: ListTabComponent;
    
    protected areBeansEqual(b1: TransactionBean, b2: TransactionBean):boolean{
        return b1 == b2 || b1.transactionCode.value == b2.transactionCode.value;
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
    
    override ngOnInit(): void {

        super.ngOnInit()

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

}
