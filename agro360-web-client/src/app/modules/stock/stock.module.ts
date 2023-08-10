import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { StockRoutingModule } from './stock-routing.module';
import { IndexPageComponent } from './article/index.page.component';
import { EditPageComponent } from './article/edit.page.component';

@NgModule({
    declarations: [
        IndexPageComponent,
        EditPageComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        StockRoutingModule
    ],
    providers: [

    ]
})
export class StockModule { }