import { Component, Input, OnInit } from '@angular/core';
import { FieldMetadata } from 'src/app/backed/metadata';


@Component({
    selector: 'input-checkbox-field',
    template: `<mat-checkbox type="checkbox" [(ngModel)]="field.value"><label>{{label}}</label></mat-checkbox>`
})
export class InputCheckboxFieldComponent implements OnInit {

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
