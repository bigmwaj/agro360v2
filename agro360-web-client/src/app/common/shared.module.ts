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
    ]
})
export class SharedModule { }