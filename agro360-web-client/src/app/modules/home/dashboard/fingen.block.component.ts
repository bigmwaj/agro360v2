import { Component, OnInit } from '@angular/core';
import { SharedModule } from 'src/app/modules/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule,     
    ],
    selector: 'home-dashboard-fingen-block',
    templateUrl: './fingen.block.component.html'
})
export class FinGenBlockComponent implements OnInit {

    ngOnInit(): void {
    }
}
