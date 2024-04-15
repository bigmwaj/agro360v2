import { CommonModule } from '@angular/common';
import { Component, Input, OnInit } from '@angular/core';
import { MatTabsModule } from '@angular/material/tabs';
import { SharedModule } from 'src/app/common/shared.module';
import { ListPageComponent } from './list.page.component';
import { FactureBean } from 'src/app/backed/bean.av';
import { EditTabComponent } from './edit.tab.component';

@Component({
    standalone: true,
    imports: [
        CommonModule,
        SharedModule,
        MatTabsModule,
        ListPageComponent,
        EditTabComponent
    ],
    selector: 'achat-vente-facture-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent implements OnInit {

    editingBeans: FactureBean[] = [];

    selectedTab: {index:number} = {index:0}

    @Input({required:true})
    module:string

    ngOnInit(): void {

    }

}