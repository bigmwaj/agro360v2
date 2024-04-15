
import { Component, Input, OnInit } from '@angular/core';
import { SharedModule } from 'src/app/common/shared.module';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { EditTabComponent } from './edit.tab.component';
import { MatTabsModule } from '@angular/material/tabs';
import { ListTabComponent } from './list.tab.component';
import { MagasinBean } from 'src/app/backed/bean.stock';
import { CommonModule } from '@angular/common';

@Component({
    standalone: true,
    imports: [
        EditTabComponent,        
        CommonModule,        
        MatToolbarModule, 
        MatIconModule,
        MatTabsModule,
        ListTabComponent
    ],
    selector: 'stock-magasin-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent implements OnInit {

    editingBeans: MagasinBean[] = [];

    selectedTab: {index:number} = {index:0}

    ngOnInit(): void {

    }
}
