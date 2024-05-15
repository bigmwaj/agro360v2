import { DecimalPipe } from '@angular/common';
import { Inject, LOCALE_ID, Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'appCurrency'
})
export class CurrencyPipe implements PipeTransform {
    decimalPipe: DecimalPipe;
    constructor(@Inject(LOCALE_ID) locale: string) {
        this.decimalPipe = new DecimalPipe(locale)
    }

    transform(value: any, digitsInfo?: string, locale?: string) {
        let di = "";
        if (digitsInfo) {
            di = digitsInfo;
        } else {
            di = ".2-2"
        }
        return this.decimalPipe.transform(value, di, locale);
    }
}