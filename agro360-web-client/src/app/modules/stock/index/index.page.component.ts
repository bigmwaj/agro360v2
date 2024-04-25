import { Component, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { MatTabGroup, MatTabsModule } from '@angular/material/tabs';
import { IndexPageComponent as ArticleIndexPageComponent } from '../article/index.page.component';
import { IndexPageComponent as MagasinIndexPageComponent } from '../magasin/index.page.component';
import { IndexPageComponent as InventaireIndexPageComponent } from '../inventaire/index.page.component';
import { BreadcrumbItem } from 'src/app/modules/common/service/ui.service';

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
    
    selectedTab: {index:number} = {index:0}

    breadcrumb:BreadcrumbItem

    @ViewChild('stock')
    tabGroup: MatTabGroup;
    
    @ViewChildren("indexPage") 
    indexPage!: QueryList<ArticleIndexPageComponent
        | MagasinIndexPageComponent
        | InventaireIndexPageComponent>;

    ngOnInit(): void {
        if( !this.breadcrumb ){
            this.breadcrumb = new BreadcrumbItem('Stock')
        }
    }

    selectedTabChange($event:any):void{        
        let indexPage = this.indexPage.get($event.index);
        indexPage?.refreshPageTitle(); 
        this.selectedTab.index = $event.index;
    }
}
