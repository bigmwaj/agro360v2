import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { SharedModule } from 'src/app/shared/shared.module';
import { IndexPageComponent } from './cycle/index.page.component';
import { ProductionAvicoleRoutingModule } from './production.avicole-routing.module';

@NgModule({
    declarations: [
        IndexPageComponent
    ],
    imports: [   
        CommonModule,
        SharedModule,
        ProductionAvicoleRoutingModule
    ],
    providers: [

    ]
})
export class ProductionAvicoleModule { }