import { Component, Input, OnInit } from '@angular/core';
import { FieldMetadata } from 'src/app/backed/metadata';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

import { MatNativeDateModule } from '@angular/material/core';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldAppearance } from '@angular/material/form-field';
import { MatTooltipModule } from '@angular/material/tooltip';

@Component({
    standalone: true,
    imports:[
        CommonModule,
        FormsModule, 
        ReactiveFormsModule,
        MatNativeDateModule,
        MatDatepickerModule,
        MatInputModule, 
        MatTooltipModule
    ],
    selector: 'input-daterange-field',
    template: `
    <mat-form-field [appearance]="appearance" [class]="getCssClass()">
        <mat-label *ngIf="displayLabel">{{label}}</mat-label>
        <mat-date-range-input    
            [rangePicker]="datePicker"   
            [comparisonStart]="startField.value"
            [comparisonEnd]="endField.value">
            <input matStartDate placeholder="Start date"  [(ngModel)]="startField.value"
                [disabled]="!startField.editable"
                [matTooltip]="startField.tooltip"
                [required]="startField.required"/>
                
            <input matEndDate placeholder="End date" [(ngModel)]="endField.value"
                [disabled]="!endField.editable"
                [matTooltip]="endField.tooltip"
                [required]="endField.required"/>
        </mat-date-range-input>
        <mat-hint>MM/DD/YYYY – MM/DD/YYYY</mat-hint>
        <mat-datepicker-toggle matIconSuffix [for]="datePicker"></mat-datepicker-toggle>
        <mat-date-range-picker #datePicker></mat-date-range-picker>
    </mat-form-field>
    `
})
export class InputDateRangeFieldComponent implements OnInit {

    @Input()
    appearance: MatFormFieldAppearance = 'outline';

    @Input()
    startField: FieldMetadata<any>;

    @Input()
    endField: FieldMetadata<any>;

    @Input()
    label?: string;

    @Input()
    displayLabel: boolean = true;
    
    getCssClass():string{
        return 'input-field input-daterange';
    }

    ngOnInit(): void {
        if(!this.label){
            this.label = this.startField.label
        }
    }
   
}
