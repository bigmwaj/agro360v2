import { Component } from '@angular/core';
import { UIService } from '../service/ui.service';

@Component({
    standalone: true,
    selector: 'page-title',
    template: `{{ui.title}}`
})
export class PageTitleComponent{

    title:string;

    constructor(public ui: UIService){
        this.title = ui.title
    }
}
