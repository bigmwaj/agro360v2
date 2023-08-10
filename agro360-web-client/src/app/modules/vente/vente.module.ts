import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { VenteRoutingModule } from './vente-routing.module';
import { IndexPageComponent } from './commande/index.page.component';

@NgModule({
    declarations: [
        IndexPageComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        VenteRoutingModule
    ],
    providers: [

    ]
})
export class VenteModule { }