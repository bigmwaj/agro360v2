import { NgModule } from '@angular/core';
import { VenteRoutingModule } from './vente-routing.module';
import { VenteService } from './vente.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { StockModule } from '../stock/stock.module';

@NgModule({
    declarations: [
    ],
    imports: [  
        VenteRoutingModule, 
        StockModule
    ],
    providers: [
        VenteService
    ]
})
export class VenteModule { }