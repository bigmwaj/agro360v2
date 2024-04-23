import { Component, Inject, OnInit, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { CommandeBean, LigneBean } from 'src/app/backed/bean.av';
import { SharedModule } from 'src/app/common/shared.module';
import { EditReceptionListComponent } from './edit.reception.list.component';

@Component({
    standalone: true,
    imports: [
        SharedModule,
        EditReceptionListComponent
    ],
    selector: 'achat-vente-edit-reception-dialog',
    templateUrl: './edit.reception.dialog.component.html'
})
export class EditReceptionDialogComponent  implements OnInit {
    
    commande: CommandeBean

    bean: LigneBean;

    @ViewChild(EditReceptionListComponent)
    list:EditReceptionListComponent

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
