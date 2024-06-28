import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { map } from 'rxjs';
import { UserAccountBean } from 'src/app/backed/bean.core';
import { Message } from 'src/app/backed/message';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';

@Component({
    standalone: true,
    imports:[
        SharedModule
    ],
    selector: 'core-user-account-edit-dialog',
    templateUrl: './edit.dialog.component.html'
})
export class EditDialogComponent implements OnInit {

    bean: UserAccountBean;

    title: string;

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: {
            partnerCode: string,
            action: ClientOperationEnumVd
        },
        private http: HttpClient,
        private ui: UIService,
        public dialogRef: MatDialogRef<EditDialogComponent>) { }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("partnerCode", this.data.partnerCode);
        let url:string;
        switch(this.data.action){
            case ClientOperationEnumVd.CREATE:
                url = "core/user-account/create-form";
                this.title = "Création de compte utilisateur";
                break;
            case ClientOperationEnumVd.UPDATE:
                url = "core/user-account/edit-form";
                this.title = "Édition du compte utilisateur";
                break;
            default:
                throw new Error();
        }
        this.http
            .get(url, { params: queryParams })
            .subscribe(data => this.bean = <UserAccountBean>data);
    }

    saveAction() {
        this.http.post(`core/user-account`, this.bean)           
        .pipe(map((e: any) => <any>e))
        .subscribe(data => {
            this.dialogRef.close(data);
            this.ui.displayFlashMessage(<Array<Message>>data.messages);
        })
    }
}