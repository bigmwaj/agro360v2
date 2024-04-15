import { Component, OnInit, ViewChild } from '@angular/core';
import { SharedModule } from 'src/app/common/shared.module';
import { MatTabsModule } from '@angular/material/tabs';
import { IndexPageComponent as ArticleIndexPageComponent } from '../article/index.page.component';
import { IndexPageComponent as MagasinIndexPageComponent } from '../magasin/index.page.component';
import { IndexPageComponent as InventaireIndexPageComponent } from '../inventaire/index.page.component';

@Component({
    standalone: true,
    imports: [
        SharedModule,  
        MatTabsModule, 
        ArticleIndexPageComponent,
        MagasinIndexPageComponent,
        InventaireIndexPageComponent
    ],
    selector: 'stock-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent implements OnInit {

    ngOnInit(): void {
        
    }
}
