import { NgModule } from '@angular/core';
import { VenteRoutingModule } from './vente-routing.module';
import { IndexPageComponent } from './commande/index.page.component';
import { EditPageComponent } from './commande/edit.page.component';
import { CommonModule } from '@angular/common';
import { SharedModule } from 'src/app/shared/shared.module';

@NgModule({
    declarations: [
        IndexPageComponent,
        EditPageComponent
    ],
    imports: [   
        CommonModule,
        SharedModule,
        VenteRoutingModule
    ],
    providers: [

    ]
})
export class VenteModule { }