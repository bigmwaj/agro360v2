import { CommonModule } from '@angular/common';
import { Component, Input, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { MatTabGroup, MatTabsModule } from '@angular/material/tabs';
import { FactureBean } from 'src/app/backed/bean.av';
import { BreadcrumbItem } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanIndexPage } from '../../common/bean.index.page';
import { EditTabComponent } from './edit.tab.component';
import { ListTabComponent } from './list.tab.component';

@Component({
    standalone: true,
    imports: [
        CommonModule,
        SharedModule,
        MatTabsModule,
        ListTabComponent,
        EditTabComponent
    ],
    selector: 'achat-vente-facture-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent extends BeanIndexPage<FactureBean, ListTabComponent, EditTabComponent> implements OnInit {

    @Input({required:true})
    module:string;

    @Input()
    breadcrumb:BreadcrumbItem;
    
    @ViewChild('achat_vente_facture')
    tabGroup: MatTabGroup;
    
    @ViewChildren(EditTabComponent) 
    editTabs!: QueryList<EditTabComponent>;
    
    @ViewChild(ListTabComponent) 
    listTab: ListTabComponent;

    protected areBeansEqual(b1: FactureBean, b2: FactureBean):boolean{
        return b1 == b2 || b1.factureCode.value == b2.factureCode.value;
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

    override ngOnInit(): void {

        super.ngOnInit();

        if( !this.breadcrumb ){
            this.breadcrumb = new BreadcrumbItem('Factures')
        }else{
            switch(this.module){
                case 'vente': 
                    this.breadcrumb = this.breadcrumb.addAndReturnChildItem('Factures');
                    break;

                case 'achat': 
                    this.breadcrumb = this.breadcrumb.addAndReturnChildItem('Factures');
                    break;

                default:                         
                    this.breadcrumb = this.breadcrumb.addAndReturnChildItem(`Module ${this.module} inconnu!'`);
                    break;
            }
        }
    }

}
