import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogRef } from '@angular/material/dialog';
import { map } from 'rxjs';
import { CommandeBean } from 'src/app/backed/bean.av';
import { Message } from 'src/app/backed/message';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'achat-vente-commande-delete-dialog',
    templateUrl: './delete.dialog.component.html'
})
export class DeleteDialogComponent implements OnInit {

    bean: CommandeBean;

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: string,
        private http: HttpClient,
        private ui: UIService,  
        public dialogRef: MatDialogRef<DeleteDialogComponent>) { }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('commandeCode', this.data);
        this.http
            .get(`achat-vente/commande/delete-form`, { params: queryParams })
            .subscribe(data => this.bean = <CommandeBean>data);
    }

    deleteAction() {
        this.http.post(`achat-vente/commande`, this.bean)
        .pipe(map((e: any) => <any>e))
        .subscribe(data => {
            this.dialogRef.close(true);
            this.ui.displayFlashMessage(<Array<Message>>data.messages);
        })
    }
}
