import { Component } from '@angular/core';
import { AbstractFieldComponent } from './abstract.field';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';

import { MatDatetimepickerModule , MatNativeDatetimeModule } from '@mat-datetimepicker/core';


@Component({
    standalone: true,
    imports:[
        MatDatetimepickerModule,
        MatNativeDatetimeModule, 
        CommonModule,
        MatInputModule,        
        FormsModule,
    ],
    selector: 'input-email-field',
    template: `
    <mat-form-field appearance="outline" [class]="getCssClass()">
        <mat-label *ngIf="displayLabel">{{label}}</mat-label>
        <input matInput type="email" [(ngModel)]="field.value" (change)="_onChange()"/>
    </mat-form-field>
`
})
export class InputEmailFieldComponent  extends AbstractFieldComponent {

    getCssClass():string{
        return 'input-field input-email';
    }

}
