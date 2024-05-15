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
import { AutocompleteFieldComponent } from './field/autocomplete';
import { CurrencyPipe } from './pipe/currency.pipe';
import { InputPasswordFieldComponent } from './field/input.password';

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
        InputPasswordFieldComponent,
        MatSnackBarModule,        
        CommonModule,    
        MatDialogModule,
        MatCheckboxModule,
        MatTableModule,              
        MatSidenavModule,  
        MatButtonModule,
        MatIconModule,
        MatPaginatorModule,
        MatMenuModule,
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
        InputPasswordFieldComponent,
        MatButtonModule, 
        CommonModule,     
        MatIconModule,
        MatDialogModule,
        MatCheckboxModule,
        MatTableModule,              
        MatSidenavModule,
        MatPaginatorModule,
        MatMenuModule,  
        
        CurrencyPipe
        
    ],
    providers:[
    ],
    declarations:[
        CurrencyPipe
    ]
})
export class SharedModule {}