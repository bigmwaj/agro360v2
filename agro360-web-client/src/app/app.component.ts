import { Component, AfterViewInit  } from '@angular/core';
import { UIService } from './common/service/ui.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html'
})
export class AppComponent implements AfterViewInit {
    constructor(
        public ui: UIService) { }

    title = 'Agro 360';

    ngAfterViewInit(): void {
        this.title = this.ui.title;
        console.log(`Le titre de la page est ` + this.ui.title)
    }
}
