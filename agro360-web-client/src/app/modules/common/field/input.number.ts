import { Component } from '@angular/core';
import { AbstractFieldComponent } from './abstract.field';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatTooltipModule } from '@angular/material/tooltip';


@Component({
    standalone: true,
    imports:[
        CommonModule,
        MatInputModule,        
        FormsModule,
        MatTooltipModule
    ],
    selector: 'input-number-field',
    template: `
    <mat-form-field [appearance]="appearance" [class]="getCssClass()">
        <mat-label *ngIf="displayLabel">{{label}}</mat-label>
        <input matInput type="number" 
            [class]="inputCssClass"      
            [matTooltip]="field.tooltip"       
            [min]="field.min || field.min == 0 ? field.min : -9999999"
            [max]="field.max || field.max == 0 ? field.max : 9999999"
            [(ngModel)]="field.value" (change)="_onChange($event)"
            [disabled]="!field.editable"
            [required]="field.required"/>
    </mat-form-field>`
})
export class InputNumberFieldComponent  extends AbstractFieldComponent {

    getCssClass():string{
        return 'input-field input-number';
    }
   
}
