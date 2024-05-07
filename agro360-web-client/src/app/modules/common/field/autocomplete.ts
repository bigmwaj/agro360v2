import { AsyncPipe, CommonModule } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { map } from 'rxjs';
import { AbstractFieldComponent } from './abstract.field';
import { MatTooltipModule } from '@angular/material/tooltip';

@Component({
    standalone: true,
    imports:[
        CommonModule,
        MatInputModule,        
        FormsModule,
        MatAutocompleteModule,
        FormsModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        AsyncPipe,
        MatTooltipModule
    ],
    selector: 'autocomplete-field',
    template: `    
    <mat-form-field class="example-full-width">
        <mat-label *ngIf="displayLabel">{{label}}</mat-label>
        <input type="text" [class]="inputCssClass" (keydown)="lookup()"
            placeholder="Sélectionnez-en un!"
            aria-label="Recherche rapide"
            matInput
            [matTooltip]="field.tooltip"
            (change)="_onChange($event)"
            [(ngModel)]="field.value"
            [matAutocomplete]="auto"/>
        <mat-autocomplete #auto="matAutocomplete" (optionSelected)="__onSelected($event)">
            <mat-option *ngFor="let option of options; let index = index" [value]="option.alias.value">
                {{ displayFn(option) }}
            </mat-option>
        </mat-autocomplete>
    </mat-form-field>    
    `
})
export class AutocompleteFieldComponent extends AbstractFieldComponent{
   
    @Input({required: true})
    lookupUrl: string;

    @Input({required: true})
    displayFn: (option:any) => string;

    @Output()
    onSelected = new EventEmitter();

    __onSelected($event:any){
        this.onSelected.emit($event)
    }

    options: Array<any> = [];

    constructor(public http: HttpClient){
        super()
    }

    lookup(){
        const query = this.field.value;
        if( query == null || query.length < 3 ){
            this.options = [];
        }

        let queryParams = new HttpParams();
        queryParams = queryParams.append("query", query);

        this.http
            .get(this.lookupUrl, { params: queryParams })
            .pipe(
                map((data: any) => <Array<any>>data
            )).subscribe(options => this.options = options);
    }

    getCssClass():string{
        return 'input-complete input-text';
    }
}
