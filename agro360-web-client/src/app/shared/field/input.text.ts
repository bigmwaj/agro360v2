import { Component, Input, OnInit } from '@angular/core';
import { FieldMetadata } from 'src/app/backed/metadata';


@Component({
    selector: 'input-text-field',
    template: `
    <mat-form-field>
        <mat-label>{{label}}</mat-label>
        <input matInput type="text"  [(ngModel)]="field.value"/>
    </mat-form-field>`
})
export class InputTextFieldComponent implements OnInit {

    @Input()
    field: FieldMetadata<any>;

    @Input()
    label?: string;

    @Input()
    id?: string;

    constructor() { }

    ngOnInit(): void {
        
    }
   
}
