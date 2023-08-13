import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatIconModule } from '@angular/material/icon';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { BrowserModule } from '@angular/platform-browser';
import { SharedModule } from 'src/app/shared/shared.module';
import { BlockComponent as CategoryBlockComponent } from './category/block.component';
import { EditModalComponent as CategoryEditModalComponent } from './category/edit.modal.component';
import { TiersRoutingModule } from './tiers-routing.module';
import { CategoryBlockComponent as TiersCategoryBlockComponent } from './tiers/category.block.component';
import { EditPageComponent } from './tiers/edit.page.component';
import { IndexPageComponent } from './tiers/index.page.component';
import { MatGridListModule } from '@angular/material/grid-list';

@NgModule({
    declarations: [
        EditPageComponent,
        TiersCategoryBlockComponent,
        CategoryEditModalComponent,
        CategoryBlockComponent,
        IndexPageComponent,
    ],
    imports: [
        TiersRoutingModule,
        CommonModule,
        BrowserModule,
        SharedModule,
        MatTableModule,
        MatCheckboxModule,
        MatPaginatorModule,
        MatIconModule,
        MatButtonModule,
        MatGridListModule
    ],
    providers: [

    ]
})
export class TiersModule { }