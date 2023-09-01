import { NgModule } from '@angular/core';
import { TiersRoutingModule } from './tiers-routing.module';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule } from '@angular/material/dialog';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTableModule } from '@angular/material/table';

@NgModule({
    declarations: [
    ],

    imports: [
        TiersRoutingModule,

        CommonModule,
        MatButtonModule,
        MatIconModule,
        MatDialogModule,
        MatCheckboxModule,
        MatTableModule,
    ],

    providers: [

    ]
})
export class TiersModule { }