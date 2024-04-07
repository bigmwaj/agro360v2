
import { Component, Input, OnInit } from '@angular/core';
import { SharedModule } from 'src/app/common/shared.module';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { EditTabComponent } from './edit.tab.component';
import { MatTabsModule } from '@angular/material/tabs';
import { ListPageComponent } from './list.page.component';
import { PartnerBean } from 'src/app/backed/bean.core';

@Component({
    standalone: true,
    imports: [
        EditTabComponent,
        SharedModule,        
        MatToolbarModule, 
        MatIconModule,
        MatTabsModule,
        ListPageComponent
    ],
    selector: 'core-partner-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent implements OnInit {

    editingBeans: PartnerBean[] = [];

    selectedTab: {index:number} = {index:0}

    ngOnInit(): void {

    }
}
