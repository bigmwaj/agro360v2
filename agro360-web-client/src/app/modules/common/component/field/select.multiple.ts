import { Component } from '@angular/core';
import { AbstractFieldComponent } from './abstract.field';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatTooltipModule } from '@angular/material/tooltip';

@Component({
    standalone: true,
    imports:[
        CommonModule,     
        FormsModule,
        MatSelectModule,
        MatFormFieldModule,
        MatTooltipModule
    ],
    selector: 'select-multiple-field',
    template: `    
    <mat-form-field [appearance]="appearance" [class]="getCssClass()">
        <mat-label *ngIf="displayLabel">{{label}}</mat-label>
        <mat-select multiple 
            [(ngModel)]="field.value" 
            (change)="_onChange($event)"
            [disabled]="!field.editable" 
            [matTooltip]="field.tooltip"
            [class]="inputCssClass"
            [required]="field.required">
                <mat-option *ngFor="let o of keys(field.valueOptions)" [value]="o">{{ field.valueOptions[o] }}</mat-option>
        </mat-select>
    </mat-form-field>`
})
export class SelectMultipleFieldComponent  extends AbstractFieldComponent {

    getCssClass():string{
        return 'input-field select-multiple';
    }

    keys(object: any) {
        if (object) {
            return Object.keys(object)
        }
        return []
    }
}
