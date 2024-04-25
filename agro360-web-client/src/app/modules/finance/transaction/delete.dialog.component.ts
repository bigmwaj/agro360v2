import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { map } from 'rxjs';
import { TransactionBean } from 'src/app/backed/bean.finance';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'finance-transaction-delete-dialog',
    templateUrl: './delete.dialog.component.html'
})
export class DeleteDialogComponent implements OnInit {

    bean: TransactionBean;

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: any,
        private http: HttpClient,
        private ui: UIService,
        public dialog: MatDialog) { }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('transactionCode', this.data.transactionCode)
        this.http
            .get(`finance/transaction/delete-form`, { params: queryParams })    
            .subscribe(data => this.bean = <TransactionBean>data);
    }

    deleteAction() {
        this.http.post(`finance/transaction`, this.bean)   
            .pipe(map((data: any) => data))
            .subscribe(data => 
                {
                    this.ui.displayFlashMessage(data.messages);                    
                    this.dialog.closeAll();
                })
    }  
}
