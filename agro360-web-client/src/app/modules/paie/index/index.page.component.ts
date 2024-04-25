import { Component, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { MatTabGroup, MatTabsModule } from '@angular/material/tabs';
import { IndexPageComponent as TransactionIndexPageComponent } from '../../finance/transaction/index.page.component';
import { IndexPageComponent as PartnerIndexPageComponent } from '../../core/partner/index.page.component';
import { BreadcrumbItem } from 'src/app/modules/common/service/ui.service';

@Component({
    standalone: true,
    imports: [
        SharedModule,  
        MatTabsModule, 
        TransactionIndexPageComponent,
        PartnerIndexPageComponent
    ],
    selector: 'paie-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent implements OnInit {

    
    module:string = 'paie';

    breadcrumb:BreadcrumbItem

    @ViewChild('paie')
    tabGroup: MatTabGroup;
    
    @ViewChildren("indexPage") 
    indexPage!: QueryList< PartnerIndexPageComponent | TransactionIndexPageComponent>;

    ngOnInit(): void {
        if( !this.breadcrumb ){
            this.breadcrumb = new BreadcrumbItem('Paie')
        }
    }

    selectedTabChange($event:any):void{        
        let indexPage = this.indexPage.get($event.index);
        indexPage?.refreshPageTitle(); 
        
    }
}
