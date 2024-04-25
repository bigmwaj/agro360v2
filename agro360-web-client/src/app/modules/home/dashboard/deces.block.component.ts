import { Component, OnInit } from '@angular/core';
import { SharedModule } from 'src/app/modules/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule,     
    ],
    selector: 'home-dashboard-deces-block',
    templateUrl: './deces.block.component.html'
})
export class DecesBlockComponent implements OnInit {

    ngOnInit(): void {
        
    }
}
