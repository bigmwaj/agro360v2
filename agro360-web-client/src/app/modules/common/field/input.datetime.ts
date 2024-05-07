import { Component } from '@angular/core';
import { AbstractFieldComponent } from './abstract.field';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';

import { MatDatetimepickerModule , MatNativeDatetimeModule } from '@mat-datetimepicker/core';
import { MatTooltipModule } from '@angular/material/tooltip';

@Component({
    standalone: true,
    imports:[
        MatDatetimepickerModule,
        MatNativeDatetimeModule, 
        CommonModule,
        MatInputModule,        
        FormsModule,
        MatTooltipModule
    ],
    selector: 'input-datetime-field',
    template: `
        <mat-form-field [appearance]="appearance" [class]="getCssClass()">
            <mat-label *ngIf="displayLabel">{{label}}</mat-label>
            <input matInput [matDatetimepicker]="datetimePicker" [class]="inputCssClass"
                [(ngModel)]="field.value" 
                (change)="_onChange($event)" 
                [matTooltip]="field.tooltip"               
                [disabled]="!field.editable"
                [required]="field.required"/>
            <mat-datetimepicker-toggle matIconSuffix [for]="datetimePicker"></mat-datetimepicker-toggle>
            <mat-datetimepicker #datetimePicker></mat-datetimepicker>
        </mat-form-field>
`
})
export class InputDateTimeFieldComponent  extends AbstractFieldComponent {

    getCssClass():string{
        return 'input-field input-datetime';
    }
}
