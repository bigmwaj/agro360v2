import { Component, Input, OnInit, EventEmitter, Output } from '@angular/core';
import { MatFormFieldAppearance } from '@angular/material/form-field';
import { AbstractBean } from 'src/app/backed/bean.common';
import { FieldMetadata } from 'src/app/backed/metadata';
import { ClientOperationEnumVd, MessageTypeEnumVd } from 'src/app/backed/vd.common';

@Component({
    selector: 'field-wrapper',
    template: ``
})
export abstract class AbstractFieldComponent implements OnInit {

    @Input()
    field: FieldMetadata<any>;

    @Input()
    owner: AbstractBean;

    @Input()
    label?: string;

    @Input()
    inputCssClass?: string;

    @Input()
    appearance: MatFormFieldAppearance = 'fill';

    @Input()
    displayLabel: boolean = true;

    @Output()
    onChange = new EventEmitter();

    abstract getCssClass():string;

    private hasMessage(type:MessageTypeEnumVd):boolean{
        if(!this.field || !this.field.messages){
            return false;
        }

        return this.field.messages.some(m => type == m.type);
    }

    hasErrorMessage():boolean{
        return this.hasMessage(MessageTypeEnumVd.ERROR);
    }
    
    private getMessage(type:MessageTypeEnumVd): string{
        if(!this.field || !this.field.messages){
            return '';
        }
        return this.field.messages.filter(m => type == m.type).map(m => m.message).join(",");
    }

    getErrorMessage(): string{
        return this.getMessage(MessageTypeEnumVd.ERROR)
    }

    _onChange($event:any){
        this.onChange.emit($event)
        this.field.valueChanged = true;
        if( this.owner && this.owner.action == ClientOperationEnumVd.SYNC){
            this.owner.action = ClientOperationEnumVd.UPDATE
        }
    }

    ngOnInit(): void {
        if(!this.label){
            this.label = this.field.label
        }
    }
   
}
