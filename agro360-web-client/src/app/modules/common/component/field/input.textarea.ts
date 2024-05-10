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
    selector: 'input-textarea-field',
    template: `
    <mat-form-field [appearance]="appearance" [class]="getCssClass()">
        <mat-label *ngIf="displayLabel">{{label}}</mat-label>
        <textarea matInput [(ngModel)]="field.value" (change)="_onChange($event)"
            [maxLength]="field.maxLength && field.maxLength > 0 ? field.maxLength : 256"
            [disabled]="!field.editable"
            [class]="inputCssClass"
            [matTooltip]="field.tooltip"
            [required]="field.required"></textarea>
    </mat-form-field>
`
})
export class InputTextareaFieldComponent  extends AbstractFieldComponent {

    getCssClass():string{
        return 'input-field input-textarea';
    }
   
}
