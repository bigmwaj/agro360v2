import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IndexPageComponent } from './cycle/index.page.component';
import { ProductionAvicoleRoutingModule } from './production.avicole-routing.module';

@NgModule({
    declarations: [
        IndexPageComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        ProductionAvicoleRoutingModule
    ],
    providers: [

    ]
})
export class ProductionAvicoleModule { }