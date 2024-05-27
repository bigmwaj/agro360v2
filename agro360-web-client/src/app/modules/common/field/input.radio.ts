import { Component } from '@angular/core';
import { AbstractFieldComponent } from './abstract.field';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatRadioModule } from '@angular/material/radio';

@Component({
    standalone: true,
    imports: [
        CommonModule,
        MatInputModule,
        FormsModule,
        MatFormFieldModule,
        MatTooltipModule,
        MatRadioModule
    ],
    selector: 'input-radio-field',
    template: `           
        <mat-label *ngIf="displayLabel">{{label}}</mat-label>
        <br/>
        <mat-radio-group [(ngModel)]="field.value" (selectionChange)="_onChange($event)"
            [disabled]="!field.editable" 
            [class]="inputCssClass"
            [matTooltip]="field.tooltip"
            [required]="field.required">
            <mat-radio-button *ngFor="let k of keys(field.valueOptions)" [value]="cast(k)">{{ field.valueOptions[k] }}</mat-radio-button>           
        </mat-radio-group>`
})
export class InputRadioFieldComponent extends AbstractFieldComponent {

    cast(value:any):any{
        if( (typeof this.field.value) == 'number'){
            return parseFloat(value) ;
        }
        return value;
    }

    getCssClass(): string {
        return 'input-field input-radio';
    }

    keys(object: any) {
        if (object) {
            return Object.keys(object)
        }
        return []
    }

}
