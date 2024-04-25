import { Component } from '@angular/core';
import { AbstractFieldComponent } from './abstract.field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';

@Component({
    standalone: true,
    imports: [
        CommonModule,
        FormsModule, 
        ReactiveFormsModule,
        MatFormFieldModule,
        MatInputModule,
        MatNativeDateModule,
        MatDatepickerModule,
    ],
    selector: 'input-date-field',
    template: `
        <mat-form-field [appearance]="appearance" [class]="getCssClass()">
            <mat-label *ngIf="displayLabel">{{label}}</mat-label>
            <input matInput 
                [matDatepicker]="picker" 
                [(ngModel)]="field.value" 
                (change)="_onChange()" 
                [disabled]="!field.editable"
                [required]="field.required"/>
            <mat-datepicker-toggle matIconSuffix [for]="picker"></mat-datepicker-toggle>
            <mat-datepicker #picker></mat-datepicker>
        </mat-form-field>
    `
})
export class InputDateFieldComponent extends AbstractFieldComponent {

    getCssClass(): string {
        return 'input-field input-date';
    }
}
