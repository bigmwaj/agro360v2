import { Component, OnInit } from '@angular/core';
import { BreadcrumbItem, UIService } from 'src/app/common/service/ui.service';
import { SharedModule } from 'src/app/common/shared.module';
import { AvicoleBlockComponent } from './avicole.block.component';
import { CompteBlockComponent } from './compte.block.component';
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
    
    breadcrumb:BreadcrumbItem;

    constructor(
        private ui: UIService){
    }

    ngOnInit(): void {        
        this.breadcrumb = new BreadcrumbItem("Tableau de bord")
    }

    ngAfterViewInit(): void {
        this.ui.setBreadcrumb(this.breadcrumb)
    }
}
