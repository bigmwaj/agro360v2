import { Component, OnInit } from '@angular/core';
import { SharedModule } from 'src/app/common/shared.module';
import { CompteBlockComponent } from './compte.block.component';
import { AvicoleBlockComponent } from './avicole.block.component';
import { FinGenBlockComponent } from './fingen.block.component';

@Component({
    standalone: true,
    imports: [
        CompteBlockComponent,
        FinGenBlockComponent,
        AvicoleBlockComponent,
        SharedModule,     
    ],
    selector: 'home-dashboard-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent implements OnInit {
    
    ngOnInit(): void {
        
    }
}
