import { Component } from '@angular/core';
import { AbstractFieldComponent } from './abstract.field';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'
import { MatInputModule } from '@angular/material/input';

@Component({
    standalone: true,
    imports:[
        CommonModule,
        MatInputModule,        
        FormsModule,
    ],
    selector: 'input-tel-field',
    template: `
    <mat-form-field appearance="outline" [class]="getCssClass()">
        <mat-label *ngIf="displayLabel">{{label}}</mat-label>
        <input matInput type="tel" [(ngModel)]="field.value" (change)="_onChange()"/>
    </mat-form-field>
`
})
export class InputTelFieldComponent  extends AbstractFieldComponent {

    getCssClass():string{
        return 'input-field input-tel';
    }
   
}
