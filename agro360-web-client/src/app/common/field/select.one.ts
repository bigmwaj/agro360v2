import { Component } from '@angular/core';
import { AbstractFieldComponent } from './abstract.field';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatFormFieldModule } from '@angular/material/form-field';

@Component({
    standalone: true,
    imports: [
        CommonModule,
        MatInputModule,
        FormsModule,
        MatSelectModule,
        MatFormFieldModule
    ],
    selector: 'select-one-field',
    template: `    
    <mat-form-field appearance="outline" [class]="getCssClass()">
        <mat-label *ngIf="displayLabel">{{label}}</mat-label>
        <mat-select [(ngModel)]="field.value" (selectionChange)="_onChange()">
            <mat-option>--</mat-option>    
            <ng-container *ngFor="let o of keys(field.valueOptions)">
                <mat-option [value]="o">{{ field.valueOptions[o] }}</mat-option>
            </ng-container>
        </mat-select>
    </mat-form-field>`
})
export class SelectOneFieldComponent extends AbstractFieldComponent {

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
