import { NgModule } from '@angular/core';
import { PaieRoutingModule } from './paie-routing.module';
import { PaieService } from './paie.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { StockModule } from '../stock/stock.module';

@NgModule({
    declarations: [
    ],
    imports: [  
        PaieRoutingModule, 
        SharedModule,
        StockModule
    ],
    providers: [
        PaieService
    ]
})
export class PaieModule { }