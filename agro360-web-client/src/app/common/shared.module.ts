import { NgModule } from '@angular/core';
import { InputCheckboxFieldComponent } from './field/input.checkbox';
import { InputDateFieldComponent } from './field/input.date';
import { InputDateRangeFieldComponent } from './field/input.daterange';
import { InputDateTimeFieldComponent } from './field/input.datetime';
import { InputEmailFieldComponent } from './field/input.email';
import { InputNumberFieldComponent } from './field/input.number';
import { InputTelFieldComponent } from './field/input.tel';
import { InputTextFieldComponent } from './field/input.text';
import { InputTextareaFieldComponent } from './field/input.textarea';
import { SelectMultipleFieldComponent } from './field/select.multiple';
import { SelectOneFieldComponent } from './field/select.one';
import { PageTitleComponent } from './page-title.component';
import { TiersService } from '../modules/tiers/tiers.service';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { CommonModule } from '@angular/common';
import { MatDialogModule } from '@angular/material/dialog';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTableModule } from '@angular/material/table';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatPaginatorModule } from '@angular/material/paginator';

@NgModule({
    imports: [ 
        InputCheckboxFieldComponent,
        InputDateFieldComponent,
        InputDateRangeFieldComponent,
        InputDateTimeFieldComponent,
        InputEmailFieldComponent,
        InputNumberFieldComponent,
        InputTelFieldComponent,
        InputTextareaFieldComponent,
        InputTextFieldComponent,
        SelectMultipleFieldComponent,
        SelectOneFieldComponent,
        PageTitleComponent,
        MatSnackBarModule,        
        MatButtonModule,
        MatIconModule,         
        CommonModule,    
        MatButtonModule,
        MatIconModule,
        MatDialogModule,
        MatCheckboxModule,
        MatTableModule,              
        MatSidenavModule,        
        CommonModule,
        MatButtonModule,
        MatIconModule,
        MatDialogModule,
        MatCheckboxModule,
        MatTableModule,
        MatPaginatorModule
    ],
    exports: [
        InputCheckboxFieldComponent,
        InputDateFieldComponent,
        InputDateRangeFieldComponent,
        InputDateTimeFieldComponent,
        InputEmailFieldComponent,
        InputNumberFieldComponent,
        InputTelFieldComponent,
        InputTextareaFieldComponent,
        InputTextFieldComponent,
        SelectMultipleFieldComponent,
        SelectOneFieldComponent, 
        MatButtonModule, 
        CommonModule,     
        MatIconModule,
        MatDialogModule,
        MatCheckboxModule,
        MatTableModule,              
        MatSidenavModule,
        MatPaginatorModule
        
    ]
})
export class SharedModule { }