import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { map } from 'rxjs';
import { FactureBean } from 'src/app/backed/bean.av';
import { Message } from 'src/app/backed/message';
import { UIService } from 'src/app/common/service/ui.service';
import { SharedModule } from 'src/app/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'achat-vente-facture-delete-dialog',
    templateUrl: './delete.dialog.component.html'
})
export class DeleteDialogComponent implements OnInit {

    bean: FactureBean;

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: string,
        private http: HttpClient,
        public dialog: MatDialog,
        private ui: UIService) { }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('factureCode', this.data);
        this.http
            .get(`achat-vente/facture/delete-form`, { params: queryParams })
            .subscribe(data => this.bean = <FactureBean>data);
    }

    deleteAction() {
        this.http.post(`achat-vente/facture`, this.bean)
        .pipe(map((e: any) => <any>e))
        .subscribe(data => {
            this.dialog.closeAll();
            this.ui.displayFlashMessage(<Array<Message>>data.messages);
        })
    }
}
