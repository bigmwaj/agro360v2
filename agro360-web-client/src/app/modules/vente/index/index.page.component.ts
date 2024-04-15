import { Component, OnInit } from '@angular/core';
import { MatTabsModule } from '@angular/material/tabs';
import { IndexPageComponent as CommandeIndexPageComponent } from '../../achat-vente/commande/index.page.component';
import { IndexPageComponent as FactureIndexPageComponent } from '../../achat-vente/facture/index.page.component';
import { IndexPageComponent as PartnerIndexPageComponent } from '../../core/partner/index.page.component';
import { IndexPageComponent as TransactionIndexPageComponent } from '../../finance/transaction/index.page.component';

@Component({
    standalone: true,
    imports: [  
        MatTabsModule, 
        TransactionIndexPageComponent,
        PartnerIndexPageComponent,
        CommandeIndexPageComponent,
        FactureIndexPageComponent
    ],
    selector: 'vente-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent implements OnInit {

    module:string = 'vente'

    ngOnInit(): void {
        
    }

    selectedTabChange($event:any):void{
        console.log('Tab changed ...')
    }
}