import { FormControl, FormGroup } from "@angular/forms";
import { EditableListAttribute } from "./editable-list.attribute";

export abstract class EditableListElement{

    isnew = false;
    
    deleted = false;
    
    updated = false; 

    abstract updateAllowed():boolean;

    abstract deleteAllowed():boolean;

    abstract equals(e:EditableListElement):boolean

    private formGroup:FormGroup;

    getValue(attribute:EditableListAttribute):any{
        const _this:any = this
        
        const val = _this[attribute.name]
        if( val === undefined ){
            if( attribute.defaultValue ){
                return attribute.defaultValue;
            }
            switch(attribute.type){
                case 'checkbox': return false;
                case 'number': return 0;
                default: return '';
            }
        }
        return val;
    }

    isDeletable():boolean{   
        return !this.isnew && !this.deleted && this.deleteAllowed() && !this.updated
    }

    isUpdatable():boolean{
        return !this.isnew && !this.deleted && !this.updated && this.updateAllowed()
    }

    isCancelable():boolean{
        return this.isnew || this.deleted || this.updated;
    }

    isSavable():boolean{
        return this.isnew || (this.deleted && this.deleteAllowed()) || (this.updated && this.updateAllowed());
    }

    initDelete(){
        this.deleted = true
    }

    initUpdate(){
        this.updated = true
    }

    onChange(event:any, name:string){
        const dto:any = this
        dto[name] = event.target.value;
    }

    toggleChecked(event:any, name:string){
        const dto:any = this
        dto[name] = !dto[name] ;
    }

}