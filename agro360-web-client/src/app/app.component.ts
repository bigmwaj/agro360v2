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

    ngAfterViewInit(): void {
        this.ui.setPageTitle(this.pageTitle)
    }
}
