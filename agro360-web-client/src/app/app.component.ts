import { Component, ViewChild, ElementRef } from '@angular/core';
import { UIService } from './common/service/ui.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html'
})
export class AppComponent  {
    constructor(public ui: UIService) { }

    @ViewChild("pageTitle")
    pageTitle:ElementRef;

    ngOnInit(): void {

    }

    ngAfterViewInit(): void {
        console.log('AppComponent ... ')
        this.ui.setPageTitle(this.pageTitle)
    }
}
