import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, EventEmitter, Inject, OnInit, Output } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { TransactionBean } from 'src/app/backed/bean.finance';
import { SharedModule } from 'src/app/common/shared.module';

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
        public dialog: MatDialog) { }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("transactionCode", this.data.transactionCode);
        this.http
            .get("finance/transaction/change-status-form", { params: queryParams })
            .subscribe(data => this.bean = <TransactionBean>data);
    }

    changeStatusAction() {
        this.http.post(`finance/transaction`, this.bean).subscribe(data => {
            console.log(data);
            this.dialog.closeAll();
        })
    }
    
}
