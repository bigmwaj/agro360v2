import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AchatRoutingModule } from './achat-routing.module';
import { IndexPageComponent } from './bon-commande/index.page.component';

@NgModule({
    declarations: [
        IndexPageComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        AchatRoutingModule
    ],
    providers: [

    ]
})
export class AchatModule { }