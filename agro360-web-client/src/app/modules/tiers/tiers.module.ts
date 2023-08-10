import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BlockComponent as CategoryBlockComponent } from './category/block.component';
import { EditModalComponent as CategoryEditModalComponent } from './category/edit.modal.component';
import { TiersRoutingModule } from './tiers-routing.module';
import { CategoryBlockComponent as TiersCategoryBlockComponent } from './tiers/category.block.component';
import { EditPageComponent } from './tiers/edit.page.component';
import { IndexPageComponent } from './tiers/index.page.component';

@NgModule({
    declarations: [
        IndexPageComponent,
        EditPageComponent,
        TiersCategoryBlockComponent,
        CategoryEditModalComponent,
        CategoryBlockComponent
    ],
    imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        TiersRoutingModule
    ],
    providers: [

    ]
})
export class TiersModule { }