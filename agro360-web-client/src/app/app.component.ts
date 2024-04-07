import { Component, AfterViewInit  } from '@angular/core';
import { UIService } from './common/service/ui.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html'
})
export class AppComponent implements AfterViewInit {
    constructor(public ui: UIService) { }

    title = 'Agro 360';

    ngAfterViewInit(): void {
        this.title = this.ui.title;

        console.log('Je ne comprends plus rien!')
        console.log(this.ui.title)

    }
}
