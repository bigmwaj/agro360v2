import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { LigneBean, RetourLigneBean } from 'src/app/backed/bean.av';
import { SharedModule } from 'src/app/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'achat-vente-edit-retour-dialog',
    templateUrl: './edit.retour.dialog.component.html'
})
export class EditRetourDialogComponent  implements OnInit {
    
    bean: LigneBean;

    beans: Array<RetourLigneBean>

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: LigneBean,
        public dialog: MatDialog) { }

    ngOnInit(): void {
        this.bean = this.data;

    }

    appliquerAction() {
        this.dialog.closeAll();
    }
}
