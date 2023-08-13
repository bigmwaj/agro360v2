import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { AchatRoutingModule } from './achat-routing.module';
import { EditPageComponent } from './bon-commande/edit.page.component';
import { IndexPageComponent } from './bon-commande/index.page.component';

@NgModule({
    declarations: [
        IndexPageComponent,
        EditPageComponent
    ],
    imports: [   
        CommonModule,
        SharedModule,
        AchatRoutingModule
    ],
    providers: [

    ]
})
export class AchatModule { }