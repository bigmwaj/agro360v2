import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatMenuModule } from '@angular/material/menu';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTableModule } from '@angular/material/table';
import { InputCheckboxFieldComponent } from './component/field/input.checkbox';
import { InputDateFieldComponent } from './component/field/input.date';
import { InputDateRangeFieldComponent } from './component/field/input.daterange';
import { InputDateTimeFieldComponent } from './component/field/input.datetime';
import { InputEmailFieldComponent } from './component/field/input.email';
import { InputNumberFieldComponent } from './component/field/input.number';
import { InputTelFieldComponent } from './component/field/input.tel';
import { InputTextFieldComponent } from './component/field/input.text';
import { InputTextareaFieldComponent } from './component/field/input.textarea';
import { SelectMultipleFieldComponent } from './component/field/select.multiple';
import { SelectOneFieldComponent } from './component/field/select.one';
import { AutocompleteFieldComponent } from './component/field/autocomplete';

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
        AutocompleteFieldComponent,
        MatSnackBarModule,        
        CommonModule,    
        MatDialogModule,
        MatCheckboxModule,
        MatTableModule,              
        MatSidenavModule,  
        MatButtonModule,
        MatIconModule,
        MatPaginatorModule,
        MatMenuModule
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
        AutocompleteFieldComponent,
        SelectMultipleFieldComponent,
        SelectOneFieldComponent, 
        MatButtonModule, 
        CommonModule,     
        MatIconModule,
        MatDialogModule,
        MatCheckboxModule,
        MatTableModule,              
        MatSidenavModule,
        MatPaginatorModule,
        MatMenuModule,
        
        
    ],
    providers:[
    ]
})
export class SharedModule {}