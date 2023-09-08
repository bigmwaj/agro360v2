import { NgModule } from '@angular/core';
import { VenteRoutingModule } from './vente-routing.module';
import { VenteService } from './vente.service';
import { SharedModule } from 'src/app/common/shared.module';

@NgModule({
    declarations: [
    ],
    imports: [  
        VenteRoutingModule, 
        SharedModule
    ],
    providers: [
        VenteService
    ]
})
export class VenteModule { }