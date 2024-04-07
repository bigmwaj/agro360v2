import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { CommandeBean } from 'src/app/backed/bean.av';
import { SharedModule } from 'src/app/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'achat-vente-commande-changeStatus-dialog',
    templateUrl: './change-status.dialog.component.html',
})
export class ChangeStatusDialogComponent implements OnInit {

    bean: CommandeBean;

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: string,
        private http: HttpClient,
        public dialog: MatDialog) { }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('commandeCode', this.data);
        this.http
            .get(`achat-vente/commande/change-status-form`, { params: queryParams })
            .subscribe(data => this.bean = <CommandeBean>data);
    }

    changeStatusAction() {
        this.http.post(`achat-vente/commande`, this.bean).subscribe(data => {
            console.log(data);
            this.dialog.closeAll();
        })
    }
}
