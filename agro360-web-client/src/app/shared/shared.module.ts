import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { InputCheckboxFieldComponent } from './field/input.checkbox';
import { InputDateFieldComponent } from './field/input.date';
import { InputDateTimeFieldComponent } from './field/input.datetime';
import { InputEmailFieldComponent } from './field/input.email';
import { InputPhoneFieldComponent } from './field/input.phone';
import { InputTextFieldComponent } from './field/input.text';
import { InputTextareaFieldComponent } from './field/input.textarea';
import { SelectOneFieldComponent } from './field/select.one';
import { MatTreeModule } from '@angular/material/tree';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';

@NgModule({
    declarations: [
        InputTextFieldComponent,
        InputCheckboxFieldComponent,
        SelectOneFieldComponent,
        InputDateTimeFieldComponent,
        InputDateFieldComponent,
        InputEmailFieldComponent,
        InputPhoneFieldComponent,
        InputTextareaFieldComponent
    ],
    imports: [
        FormsModule,
        CommonModule,
        MatInputModule,
        MatFormFieldModule,
        MatSelectModule,
        MatCheckboxModule,
        MatTreeModule,
        MatIconModule,
        MatButtonModule

    ],
    providers: [
    ],
    exports: [
        InputTextFieldComponent,
        InputCheckboxFieldComponent,
        SelectOneFieldComponent,
        InputDateTimeFieldComponent,
        InputDateFieldComponent,
        InputEmailFieldComponent,
        InputPhoneFieldComponent,
        InputTextareaFieldComponent
    ]
})
export class SharedModule { }