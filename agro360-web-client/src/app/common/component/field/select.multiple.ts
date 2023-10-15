import { Component } from '@angular/core';
import { AbstractFieldComponent } from './abstract.field';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
    standalone: true,
    imports:[
        CommonModule,     
        FormsModule,
        MatSelectModule,
        MatFormFieldModule
    ],
    selector: 'select-multiple-field',
    template: `    
    <mat-form-field appearance="outline" [class]="getCssClass()">
        <mat-label *ngIf="displayLabel">{{label}}</mat-label>
        <mat-select multiple [(ngModel)]="field.value" (change)="_onChange()"
            [disabled]="!field.editable"
            [required]="field.required">
            <ng-container *ngFor="let o of keys(field.valueOptions)">
                <mat-option [value]="o">{{ field.valueOptions[o] }}</mat-option>
            </ng-container>
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
