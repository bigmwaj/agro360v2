import { Component, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { MatTabGroup, MatTabsModule } from '@angular/material/tabs';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { IndexPageComponent as CommandeIndexPageComponent } from '../../achat-vente/commande/index.page.component';
import { IndexPageComponent as FactureIndexPageComponent } from '../../achat-vente/facture/index.page.component';
import { IndexPageComponent as PartnerIndexPageComponent } from '../../core/partner/index.page.component';
import { IndexPageComponent as TransactionIndexPageComponent } from '../../finance/transaction/index.page.component';
import { BreadcrumbItem } from 'src/app/modules/common/service/ui.service';

@Component({
    standalone: true,
    imports: [
        SharedModule,  
        MatTabsModule, 
        TransactionIndexPageComponent,
        PartnerIndexPageComponent,
        CommandeIndexPageComponent,
        FactureIndexPageComponent
    ],
    selector: 'achat-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent implements OnInit {

    module:string = 'achat';

    breadcrumb:BreadcrumbItem

    @ViewChild('achat')
    tabGroup: MatTabGroup;
    
    @ViewChildren("indexPage") 
    indexPage!: QueryList<CommandeIndexPageComponent
        | PartnerIndexPageComponent
        | TransactionIndexPageComponent
        | FactureIndexPageComponent>;

    ngOnInit(): void {
        if( !this.breadcrumb ){
            this.breadcrumb = new BreadcrumbItem('Achats')
        }
    }

    selectedTabChange($event:any):void{        
        let indexPage = this.indexPage.get($event.index);
        indexPage?.refreshPageTitle(); 
    }
}
