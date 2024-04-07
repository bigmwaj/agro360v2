import { NgModule } from '@angular/core';
import { AchatVenteRoutingModule } from './achat-vente-routing.module';
import { SharedModule } from 'src/app/common/shared.module';
import { AchatVenteService } from './achat-vente.service';

@NgModule({
    declarations: [
        
    ],
    imports: [
        AchatVenteRoutingModule,
        SharedModule
    ],
    providers: [
        AchatVenteService
    ]
})
export class AchatVentModule { }