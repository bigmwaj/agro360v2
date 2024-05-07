import { Component } from '@angular/core';
import { AbstractFieldComponent } from './abstract.field';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'
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
    selector: 'input-tel-field',
    template: `
    <mat-form-field [appearance]="appearance" [class]="getCssClass()">
        <mat-label *ngIf="displayLabel">{{label}}</mat-label>
        <input matInput type="tel"  [class]="inputCssClass"
            [(ngModel)]="field.value" 
            [matTooltip]="field.tooltip"
            [maxLength]="field.maxLength && field.maxLength > 0 ? field.maxLength : 256"
            (change)="_onChange($event)" 
            [disabled]="!field.editable"
            [required]="field.required"/>
    </mat-form-field>
`
})
export class InputTelFieldComponent  extends AbstractFieldComponent {

    getCssClass():string{
        return 'input-field input-tel';
    }
   
}
