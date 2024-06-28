import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { map } from 'rxjs';
import { UserAccountBean } from 'src/app/backed/bean.core';
import { Message } from 'src/app/backed/message';
import { UserAccountStatusEnumVd } from 'src/app/backed/vd.core';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';

@Component({
    standalone: true,
    imports:[
        SharedModule
    ],
    selector: 'core-user-account-changeStatus-dialog',
    templateUrl: './change-status.dialog.component.html'
})
export class ChangeStatusDialogComponent implements OnInit {

    bean: UserAccountBean;

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: {partnerCode: string, status?: UserAccountStatusEnumVd},
        private http: HttpClient,
        private ui: UIService,
        public dialogRef: MatDialogRef<ChangeStatusDialogComponent>) { }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("partnerCode", this.data.partnerCode);
        if( this.data.status ){
            queryParams = queryParams.append("status", '' + this.data.status);
        }
        this.http
            .get("core/user-account/change-status-form", { params: queryParams })
            .subscribe(data => this.bean = <UserAccountBean>data);
    }

    changeStatusAction() {
        this.http.post(`core/user-account`, this.bean)           
        .pipe(map((e: any) => <any>e))
        .subscribe(data => {
            this.dialogRef.close(data);
            this.ui.displayFlashMessage(<Array<Message>>data.messages);
        })
    }
}
