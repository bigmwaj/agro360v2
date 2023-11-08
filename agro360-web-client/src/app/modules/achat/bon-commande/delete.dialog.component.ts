import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { BonCommandeBean } from 'src/app/backed/bean.achat';
import { SharedModule } from 'src/app/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'achat-bon-commande-delete-dialog',
    templateUrl: './delete.dialog.component.html'
})
export class DeleteDialogComponent implements OnInit {

    bean: BonCommandeBean;

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: string,
        private http: HttpClient,
        public dialog: MatDialog) { }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('bonCommandeCode', this.data);
        this.http
            .get(`achat/bon-commande/delete-form`, { params: queryParams })
            .subscribe(data => this.bean = <BonCommandeBean>data);
    }

    deleteAction() {
        this.http.post(`achat/bon-commande`, this.bean).subscribe(data => console.log(data))
    }
}
