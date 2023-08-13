import { Component, Input, OnInit } from '@angular/core';
import { FieldMetadata } from 'src/app/backed/metadata';


@Component({
    selector: 'input-textarea-field',
    template: `
    <mat-form-field>
        <mat-label>{{label}}</mat-label>
        <textarea matInput [(ngModel)]="field.value"></textarea>
    </mat-form-field>
`
})
export class InputTextareaFieldComponent implements OnInit {

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
