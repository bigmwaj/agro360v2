import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { map } from 'rxjs';
import { TransactionBean, TransfertBean } from 'src/app/backed/bean.finance';
import { Message } from 'src/app/backed/message';
import { UIService } from 'src/app/common/service/ui.service';
import { SharedModule } from 'src/app/common/shared.module';

@Component({
    standalone: true,
    imports:[
        SharedModule
    ],
    selector: 'finance-transaction-transfert-dialog',
    templateUrl: './transfert.dialog.component.html'
})
export class TransfertDialogComponent implements OnInit {

    bean: TransfertBean;

    constructor(
        private http: HttpClient,
        public dialog: MatDialog,
        private ui: UIService,
        public dialogRef: MatDialogRef<TransfertDialogComponent>) { }

    ngOnInit(): void {
        this.http
            .get("finance/transaction/transfert-form")
            .subscribe(data => this.bean = <TransfertBean>data);
    }

    transfertAction() {
        this.http.post(`finance/transaction/transfert`, this.bean)
        .pipe(map((e: any) => <any>e))
        .subscribe(data => {        
            this.dialogRef.close(data.records)
            this.ui.displayFlashMessage(<Array<Message>>data.messages);
        })
    }
}
