
import { Component, OnInit } from '@angular/core';
import { SharedModule } from 'src/app/common/shared.module';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { EditTabComponent } from './edit.tab.component';
import { MatTabsModule } from '@angular/material/tabs';
import { ListTabComponent } from './list.tab.component';
import { InventaireBean } from 'src/app/backed/bean.stock';

@Component({
    standalone: true,
    imports: [
        EditTabComponent,
        SharedModule,        
        MatToolbarModule, 
        MatIconModule,
        MatTabsModule,
        ListTabComponent
    ],
    selector: 'stock-inventaire-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent implements OnInit {

    editingBeans: InventaireBean[] = [];

    selectedTab: {index:number} = {index:0}

    ngOnInit(): void {

    }
}