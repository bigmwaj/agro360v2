import { NgModule } from '@angular/core';
import { StockRoutingModule } from './stock-routing.module';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { StockService } from './stock.service';

@NgModule({
    declarations: [
    ],
    imports: [        
        StockRoutingModule,
        SharedModule
    ],
    providers: [
        StockService
    ]
})
export class StockModule { }