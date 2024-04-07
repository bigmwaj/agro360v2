import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { FactureBean } from 'src/app/backed/bean.av';
import { SharedModule } from 'src/app/common/shared.module';

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
        public dialog: MatDialog) { }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('factureCode', this.data);
        this.http
            .get(`achat-vente/facture/change-status-form`, { params: queryParams })
            .subscribe(data => this.bean = <FactureBean>data);
    }

    changeStatusAction() {
        this.http.post(`achat-vente/facture`, this.bean).subscribe(data => {
            this.dialog.closeAll();
            console.log(data)
        })
    }
}
