import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, EventEmitter, Inject, OnInit, Output } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { map } from 'rxjs';
import { TransactionBean } from 'src/app/backed/bean.finance';
import { Message } from 'src/app/backed/message';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';

@Component({
    standalone: true,
    imports:[
        SharedModule
    ],
    selector: 'finance-transaction-changeStatus-dialog',
    templateUrl: './change-status.dialog.component.html'
})
export class ChangeStatusDialogComponent implements OnInit {

    bean: TransactionBean;

    @Output()
    onComplete = new EventEmitter();

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: {
            transactionCode: string
        },
        private http: HttpClient,
        public dialog: MatDialog,
        private ui: UIService) { }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("transactionCode", this.data.transactionCode);
        this.http
            .get("finance/transaction/change-status-form", { params: queryParams })
            .subscribe(data => this.bean = <TransactionBean>data);
    }

    changeStatusAction() {
        this.http.post(`finance/transaction`, this.bean)
        .pipe(map((e: any) => <any>e))
        .subscribe(data => {
            this.dialog.closeAll();
            this.ui.displayFlashMessage(<Array<Message>>data.messages);
        })
    }
    
}
