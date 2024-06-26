import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { AbstractFieldComponent } from './abstract.field';

import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatTooltipModule } from '@angular/material/tooltip';

@Component({
    standalone: true,
    imports:[
        CommonModule,
        FormsModule,
        MatCheckboxModule,
        ReactiveFormsModule,
        MatInputModule,
        MatTooltipModule 
    ],
    selector: 'input-checkbox-field',
    template: `
        <div class="mb-3" style="line-height:4" [class]="getCssClass()">
            <mat-checkbox type="checkbox"  [class]="inputCssClass"
                [(ngModel)]="field.value" 
                [matTooltip]="field.tooltip"
                (change)="_onChange($event)"
                [disabled]="!field.editable"
                [required]="field.required">
                <label *ngIf="displayLabel">{{label}}</label>
            </mat-checkbox>
        </div>`
})
export class InputCheckboxFieldComponent extends AbstractFieldComponent {

    getCssClass():string{
        return 'input-field input-checkbox';
    }
}
