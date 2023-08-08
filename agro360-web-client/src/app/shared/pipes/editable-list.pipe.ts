import { Inject, LOCALE_ID, Pipe, PipeTransform } from '@angular/core';
import { AppNumberPipe } from './app-number.pipe';
import { YesNoPipe } from './yes-no.pipe';

@Pipe({
    name: 'editableList'
})
export class EditableListPipe implements PipeTransform {


    public appNumber:AppNumberPipe
    public yesNo:YesNoPipe

    constructor(@Inject(LOCALE_ID) locale:string){
        this.yesNo = new YesNoPipe()
        this.appNumber = new AppNumberPipe(locale)
    }

    transform(value: any, ...args: any[]): any {
        if( typeof value == 'boolean' ){
            return this.yesNo.transform(value, args)
        }else if( typeof value == 'number' ){
            return this.appNumber.transform(value)
        }
        return value;
    }
}
