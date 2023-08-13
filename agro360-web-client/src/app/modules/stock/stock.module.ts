import { NgModule } from '@angular/core';
import { StockRoutingModule } from './stock-routing.module';
import { IndexPageComponent } from './article/index.page.component';
import { EditPageComponent } from './article/edit.page.component';
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
        StockRoutingModule,
    ],
    providers: [

    ]
})
export class StockModule { }