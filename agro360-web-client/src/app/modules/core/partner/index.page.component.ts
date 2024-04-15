
import { Component, Input, OnInit } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { PartnerBean } from 'src/app/backed/bean.core';
import { SharedModule } from 'src/app/common/shared.module';
import { EditTabComponent } from './edit.tab.component';
import { ListPageComponent } from './list.page.component';

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

    @Input()
    module:string

    editingBeans: PartnerBean[] = [];

    selectedTab: {index:number} = {index:0}

    ngOnInit(): void {

    }
}
