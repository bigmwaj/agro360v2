import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { TransfertBean } from 'src/app/backed/bean.finance';
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
        public dialog: MatDialog) { }

    ngOnInit(): void {
        this.http
            .get("finance/transaction/transfert-form")
            .subscribe(data => this.bean = <TransfertBean>data);
    }

    transfertAction() {
        this.http.post(`finance/transaction/transfert`, this.bean).subscribe(data => console.log(data))
    }
}
