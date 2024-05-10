import { Component } from '@angular/core';
import { AbstractFieldComponent } from './abstract.field';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatTooltipModule } from '@angular/material/tooltip';

@Component({
    standalone: true,
    imports: [
        CommonModule,
        MatInputModule,
        FormsModule,
        MatSelectModule,
        MatFormFieldModule,
        MatTooltipModule
    ],
    selector: 'select-one-field',
    template: `    
    <mat-form-field [appearance]="appearance" [class]="getCssClass()">
        <mat-label *ngIf="displayLabel">{{label}}</mat-label>
        <mat-select [(ngModel)]="field.value" (selectionChange)="_onChange($event)"
            [disabled]="!field.editable" 
            [class]="inputCssClass"
            [matTooltip]="field.tooltip"
            [required]="field.required">
            <mat-option>--</mat-option>  
            <mat-option *ngFor="let k of keys(field.valueOptions)" [value]="cast(k)">{{ field.valueOptions[k] }}</mat-option>
           
        </mat-select>
    </mat-form-field>`
})
export class SelectOneFieldComponent extends AbstractFieldComponent {

    cast(value:any):any{
        if( (typeof this.field.value) == 'number'){
            return parseFloat(value) ;
        }
        return value;
    }

    getCssClass(): string {
        return 'input-field select-one';
    }

    keys(object: any) {
        if (object) {
            return Object.keys(object)
        }
        return []
    }

}
