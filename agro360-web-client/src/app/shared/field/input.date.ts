import { Component, Input, OnInit } from '@angular/core';
import { FieldMetadata } from 'src/app/backed/metadata';


@Component({
    selector: 'input-date-field',
    template: `
        <mat-form-field>
            <mat-label>{{label}}</mat-label>
            <input matInput type="date"  [(ngModel)]="field.value"/>
        </mat-form-field>
    `
})
export class InputDateFieldComponent implements OnInit {

    @Input()
    field: FieldMetadata<any>;

    @Input()
    label?: string;

    @Input()
    id?: string;

    constructor() { 
        
    }

    ngOnInit(): void {

    }

}
