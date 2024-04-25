import { CommonModule } from '@angular/common';
import { Component, Input, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { MatTabGroup, MatTabsModule } from '@angular/material/tabs';
import { CommandeBean } from 'src/app/backed/bean.av';
import { BreadcrumbItem } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { EditTabComponent } from './edit.tab.component';
import { ListTabComponent } from './list.tab.component';
import { BeanIndexPage } from '../../common/bean.index.page';

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
export class IndexPageComponent extends BeanIndexPage<CommandeBean, ListTabComponent, EditTabComponent> {
    
    @Input({required:true})
    module:string;
    
    @Input()
    breadcrumb:BreadcrumbItem;
    
    @ViewChild('achat_vente_commande')
    tabGroup: MatTabGroup;

    @ViewChild(ListTabComponent) 
    listTab: ListTabComponent;
    
    @ViewChildren(EditTabComponent) 
    editTabs!: QueryList<EditTabComponent>;

    protected areBeansEqual(b1: CommandeBean, b2: CommandeBean):boolean{
        return b1 == b2 || b1.commandeCode.value == b2.commandeCode.value;
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

}
