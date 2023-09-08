import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { TiersBean } from 'src/app/backed/bean.tiers';
import { SharedModule } from 'src/app/common/shared.module';

const BASE_URL = "http://localhost:8080";

@Component({
    standalone: true,
    imports:[
        SharedModule
    ],
    selector: 'tiers-tiers-changeStatus-dialog',
    templateUrl: './change-status.dialog.component.html'
})
export class ChangeStatusDialogComponent implements OnInit {

    bean: TiersBean;

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: {tiersCode: string},
        private http: HttpClient,
        public dialog: MatDialog) { }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("tiersCode", this.data.tiersCode);
        this.http
            .get(BASE_URL + "/tiers/tiers/change-status-form", { params: queryParams })
            .subscribe(data => this.bean = <TiersBean>data);
    }

    changeStatusAction() {
        this.http.post(BASE_URL + `/tiers/tiers`, this.bean).subscribe(data => console.log(data))
    }
}
