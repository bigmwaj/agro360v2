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
    selector: 'input-text-field',
    template: `
    <mat-form-field appearance="outline" [class]="getCssClass()">
        <mat-label *ngIf="displayLabel">{{label}}</mat-label>
        <input matInput type="text" 
            [(ngModel)]="field.value" 
            (change)="_onChange()"
            [disabled]="!field.editable"
            [required]="field.required"/>
        <mat-error *ngIf="hasErrorMessage()">
            {{getErrorMessage()}}
        </mat-error>
        <mat-error >
            test
        </mat-error>
    </mat-form-field>`
})
export class InputTextFieldComponent extends AbstractFieldComponent {
   
    getCssClass():string{
        return 'input-field input-text';
    }
}
