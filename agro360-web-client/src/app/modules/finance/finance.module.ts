import { NgModule } from '@angular/core';
import { FinanceRoutingModule } from './finance-routing.module';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { FinanceService } from './finance.service';

@NgModule({
    declarations: [
    ],
    imports: [        
        FinanceRoutingModule,
        SharedModule
    ],
    providers: [
        FinanceService
    ]
})
export class FinanceModule { }