import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { CommandeBean, LigneBean } from 'src/app/backed/bean.av';
import { SharedModule } from 'src/app/modules/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'achat-vente-commande-edit-remise-dialog',
    templateUrl: './edit.remise.dialog.component.html',
})
export class EditRemiseDialogComponent implements OnInit {

    bean: CommandeBean|LigneBean;

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: CommandeBean|LigneBean,
        public dialog: MatDialog) { }

    ngOnInit(): void {
        this.bean = this.data

    }

    appliquerAction() {
        this.dialog.closeAll();
    }
}
