import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { map } from 'rxjs';
import { FactureBean } from 'src/app/backed/bean.av';
import { Message } from 'src/app/backed/message';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'achat-vente-facture-changeStatus-dialog',
    templateUrl: './change-status.dialog.component.html',
})
export class ChangeStatusDialogComponent implements OnInit {

    bean: FactureBean;

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: string,
        private http: HttpClient,
        private ui: UIService,   
        public dialogRef: MatDialogRef<ChangeStatusDialogComponent>) { }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('factureCode', this.data);
        this.http
            .get(`achat-vente/facture/change-status-form`, { params: queryParams })
            .subscribe(data => this.bean = <FactureBean>data);
    }

    changeStatusAction() {
        this.http.post(`achat-vente/facture`, this.bean)
        .pipe(map((e: any) => <any>e))
        .subscribe(data => {
            this.dialogRef.close(data.record);
            this.ui.displayFlashMessage(<Array<Message>>data.messages);
        })
    }
}
