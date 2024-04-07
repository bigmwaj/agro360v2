import { Component, OnInit, ViewChild } from '@angular/core';
import { SharedModule } from 'src/app/common/shared.module';
import { MatTabsModule } from '@angular/material/tabs';
import { IndexPageComponent as TransactionIndexPageComponent } from '../../finance/transaction/index.page.component';
import { IndexPageComponent as PartnerIndexPageComponent } from '../../core/partner/index.page.component';

@Component({
    standalone: true,
    imports: [
        SharedModule,  
        MatTabsModule, 
        TransactionIndexPageComponent,
        PartnerIndexPageComponent
    ],
    selector: 'paie-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent implements OnInit {

    ngOnInit(): void {

    }
}
