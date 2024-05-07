import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { CommandeBean, LigneBean } from 'src/app/backed/bean.av';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { RetourListComponent } from './retour.list.component';

@Component({
    standalone: true,
    imports: [
        SharedModule,
        RetourListComponent
    ],
    selector: 'achat-vente-retour-dialog',
    templateUrl: './retour.dialog.component.html'
})
export class RetourDialogComponent  implements OnInit {
    
    bean: CommandeBean;

    @ViewChild(RetourListComponent)
    list:RetourListComponent

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
