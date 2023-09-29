import { Component } from '@angular/core';
import { AbstractFieldComponent } from './abstract.field';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';

@Component({
    standalone: true,
    imports:[
        CommonModule,
        MatInputModule,        
        FormsModule,
    ],
    selector: 'input-textarea-field',
    template: `
    <mat-form-field appearance="outline" [class]="getCssClass()">
        <mat-label *ngIf="displayLabel">{{label}}</mat-label>
        <textarea matInput [(ngModel)]="field.value" (change)="_onChange()"></textarea>
    </mat-form-field>
`
})
export class InputTextareaFieldComponent  extends AbstractFieldComponent {

    getCssClass():string{
        return 'input-field input-textarea';
    }
   
}
