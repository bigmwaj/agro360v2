import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { CommandeBean } from 'src/app/backed/bean.av';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { ReceptionListComponent } from './reception.list.component';

@Component({
    standalone: true,
    imports: [
        SharedModule,
        ReceptionListComponent
    ],
    selector: 'achat-vente-reception-dialog',
    templateUrl: './reception.dialog.component.html'
})
export class ReceptionDialogComponent  implements OnInit {
    
    bean: CommandeBean;

    @ViewChild(ReceptionListComponent)
    list:ReceptionListComponent

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: CommandeBean,
        public dialog: MatDialog) { }

    ngOnInit(): void {
        this.bean = this.data
    }

    saveAction() {
        this.list.saveAction()
    }
}
