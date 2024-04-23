import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { CommandeBean, LigneBean } from 'src/app/backed/bean.av';
import { SharedModule } from 'src/app/common/shared.module';
import { EditRetourListComponent } from './edit.retour.list.component';

@Component({
    standalone: true,
    imports: [
        SharedModule,
        EditRetourListComponent
    ],
    selector: 'achat-vente-edit-retour-dialog',
    templateUrl: './edit.retour.dialog.component.html'
})
export class EditRetourDialogComponent  implements OnInit {
    
    commande: CommandeBean

    bean: LigneBean;

    @ViewChild(EditRetourListComponent)
    list:EditRetourListComponent

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: { ligne: LigneBean, commande: CommandeBean },
        public dialog: MatDialog) { }

    ngOnInit(): void {
        this.bean = this.data.ligne
        this.commande = this.data.commande
    }

    saveAction() {
        this.list.saveAction()
    }
}
