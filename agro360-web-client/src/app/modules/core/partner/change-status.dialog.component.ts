import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { PartnerBean } from 'src/app/backed/bean.core';
import { SharedModule } from 'src/app/common/shared.module';

@Component({
    standalone: true,
    imports:[
        SharedModule
    ],
    selector: 'core-partner-changeStatus-dialog',
    templateUrl: './change-status.dialog.component.html'
})
export class ChangeStatusDialogComponent implements OnInit {

    bean: PartnerBean;

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: {partnerCode: string},
        private http: HttpClient,
        public dialog: MatDialog) { }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("partnerCode", this.data.partnerCode);
        this.http
            .get("core/partner/change-status-form", { params: queryParams })
            .subscribe(data => this.bean = <PartnerBean>data);
    }

    changeStatusAction() {
        this.http.post(`core/partner`, this.bean)
            .subscribe(data => {
                console.log(data);
                this.dialog.closeAll();
            })
    }
}
