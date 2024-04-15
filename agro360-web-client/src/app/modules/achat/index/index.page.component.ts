import { Component, OnInit } from '@angular/core';
import { MatTabsModule } from '@angular/material/tabs';
import { SharedModule } from 'src/app/common/shared.module';
import { IndexPageComponent as CommandeIndexPageComponent } from '../../achat-vente/commande/index.page.component';
import { IndexPageComponent as FactureIndexPageComponent } from '../../achat-vente/facture/index.page.component';
import { IndexPageComponent as PartnerIndexPageComponent } from '../../core/partner/index.page.component';
import { IndexPageComponent as TransactionIndexPageComponent } from '../../finance/transaction/index.page.component';

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

    ngOnInit(): void {

    }
}