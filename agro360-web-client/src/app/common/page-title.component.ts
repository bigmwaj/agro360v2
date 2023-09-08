import { Component, Input } from '@angular/core';

@Component({
    standalone: true,
    selector: 'page-title',
    template: `{{title}}`
})
export class PageTitleComponent{

    @Input()
    title:string;
}
