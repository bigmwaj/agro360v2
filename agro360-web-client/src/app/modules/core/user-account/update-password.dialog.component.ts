import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { map } from 'rxjs';
import { UserAccountBean } from 'src/app/backed/bean.core';
import { Message } from 'src/app/backed/message';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';

@Component({
    standalone: true,
    imports:[
        SharedModule
    ],
    selector: 'core-user-account-update-password-dialog',
    templateUrl: './update-password.dialog.component.html'
})
export class UpdatePasswordDialogComponent implements OnInit {

    bean: UserAccountBean;

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: {partnerCode: string},
        private http: HttpClient,
        private ui: UIService,
        public dialogRef: MatDialogRef<UpdatePasswordDialogComponent>) { }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("partnerCode", this.data.partnerCode);
        this.http
            .get("core/user-account/edit-form", { params: queryParams })
            .subscribe(data => this.bean = <UserAccountBean>data);
    }

    updateAction() {
        this.http.post(`core/user-account`, this.bean)           
        .pipe(map((e: any) => <any>e))
        .subscribe(data => {
            this.dialogRef.close(data.record);
            this.ui.displayFlashMessage(<Array<Message>>data.messages);
        })
    }
}