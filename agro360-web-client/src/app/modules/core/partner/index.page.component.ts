
import { Component, Input, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatTabGroup, MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { PartnerBean } from 'src/app/backed/bean.core';
import { BreadcrumbItem } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanIndexPage } from '../../common/bean.index.page';
import { EditTabComponent } from './edit.tab.component';
import { ListTabComponent } from './list.tab.component';

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
export class IndexPageComponent extends BeanIndexPage<PartnerBean, ListTabComponent, EditTabComponent> implements OnInit {

    @Input()
    module:string;

    @ViewChild('core_partenaire')
    tabGroup: MatTabGroup;
    
    @ViewChildren(EditTabComponent) 
    editTabs!: QueryList<EditTabComponent>;
    
    @ViewChild(ListTabComponent) 
    listTab: ListTabComponent;
    
    @Input()
    breadcrumb:BreadcrumbItem;
    
    protected areBeansEqual(b1: PartnerBean, b2: PartnerBean):boolean{
        return b1 == b2 || b1.partnerCode.value == b2.partnerCode.value;
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
}
