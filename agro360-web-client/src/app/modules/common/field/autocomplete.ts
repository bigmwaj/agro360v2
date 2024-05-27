import { AsyncPipe, CommonModule } from '@angular/common';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { Observable, map } from 'rxjs';
import { AbstractFieldComponent } from './abstract.field';
import { MatTooltipModule } from '@angular/material/tooltip';

export interface AutocompleteConfig{
    lookupFn:(e:string, options?:any) => Observable<any>;
    displayFn: (o: any) => string;
    keyFn: (o: any) => any;
}

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
    <mat-form-field styles="100%">
        <mat-label *ngIf="displayLabel">{{label}}</mat-label>
        <input type="text" [class]="inputCssClass" 
            (keyup)="lookup()"
            placeholder="Sélectionnez-en un!"
            aria-label="Recherche rapide"
            matInput
            [matTooltip]="field.tooltip"
            (change)="_onChange($event)"
            [disabled]="!field.editable"
            [required]="field.required"
            [(ngModel)]="field.value"
            [matAutocomplete]="auto"/>
        <mat-autocomplete #auto="matAutocomplete" (optionSelected)="__onSelected($event)">
            <mat-option *ngFor="let option of options; let index = index" [value]="lookupConfig.keyFn(option)">
                {{ lookupConfig.displayFn(option) }}
            </mat-option>
        </mat-autocomplete>
    </mat-form-field>    
    `
})
export class AutocompleteFieldComponent extends AbstractFieldComponent{

    @Input({required: true})
    lookupConfig: AutocompleteConfig;

    @Input()
    lookupOptions: any;

    @Output()
    onSelected = new EventEmitter();

    __onSelected($event:any){
        this.onSelected.emit($event)
    }

    options: Array<any> = [];

    lookup(){
        const query = this.field.value;
        
        if( query == null || query.length < 2 ){
            this.options = [];
            return;
        }
        this.lookupConfig.lookupFn(query, this.lookupOptions)
        .pipe(map((data: any) => <Array<any>>data))
        .subscribe(options => this.options = options);
        
    }

    getCssClass():string{
        return 'input-autocomplete input-text';
    }
}
