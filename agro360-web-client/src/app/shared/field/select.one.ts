import { Component, Input, OnInit } from '@angular/core';
import { FieldMetadata } from 'src/app/backed/metadata';


@Component({
    selector: 'select-one-field',
    template: `
    
    <mat-form-field>
        <mat-label>{{label}}</mat-label>
        <mat-select [(ngModel)]="field.value">
            <ng-container *ngFor="let o of keys(field.valueOptions)">
                <mat-option [value]="o">{{ field.valueOptions[o] }}</mat-option>
            </ng-container>
        </mat-select>
    </mat-form-field>`
})
export class SelectOneFieldComponent implements OnInit {

    @Input()
    field: FieldMetadata<any>;

    @Input()
    label?: string;

    @Input()
    id?: string;

    constructor() { }

    ngOnInit(): void {

    }

    keys(object: any) {
        if (object) {
            return Object.keys(object)
        }
        return []
    }

}
