
import { Component, Input, OnInit } from '@angular/core';
import { TransactionBean } from 'src/app/backed/bean.finance';
import { IndexModalComponent as RubriqueIndexModalComponent } from '../rubrique/index.modal.component';
import { IndexModalComponent as CompteIndexModalComponent } from '../compte/index.modal.component';
import { SharedModule } from 'src/app/common/shared.module';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { EditTabComponent } from './edit.tab.component';
import { MatTabsModule } from '@angular/material/tabs';
import { ListPageComponent } from './list.page.component';

@Component({
    standalone: true,
    imports: [
        RubriqueIndexModalComponent,
        CompteIndexModalComponent,
        EditTabComponent,
        SharedModule,        
        MatToolbarModule, 
        MatIconModule,
        MatTabsModule,
        ListPageComponent
    ],
    selector: 'finance-transaction-index-page',
    templateUrl: './index.page.component.html'
})
export class IndexPageComponent implements OnInit {

    editingBeans: TransactionBean[] = [];

    selectedTab: {index:number} = {index:0}

    /**
     * Les différents modules qui peuvent faire appelle à cette fonctionnalité
     * vente
     * achat
     * paie
     */
    @Input()
    module:string

    ngOnInit(): void {

    }
}
