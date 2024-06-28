import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { map } from 'rxjs';
import { CommandeBean } from 'src/app/backed/bean.av';
import { Message } from 'src/app/backed/message';
import { FieldMetadata } from 'src/app/backed/metadata';
import { CommandeStatusEnumVd } from 'src/app/backed/vd.av';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';

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

    message:string;

    status: CommandeStatusEnumVd;

    currentStatusField: FieldMetadata<CommandeStatusEnumVd>

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: {
            commandeCode:string, 
            status: CommandeStatusEnumVd,
            message: string
        },
        private http: HttpClient,
        private ui: UIService,  
        public dialogRef: MatDialogRef<ChangeStatusDialogComponent>) { }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('commandeCode', this.data.commandeCode);
        if( this.data.message ){
            this.message = this.data.message;
        }
        if( this.data.status ){
            this.status = this.data.status;
        }
        this.http
            .get(`achat-vente/commande/change-status-form`, { params: queryParams })
            .subscribe(data => {
                this.bean = <CommandeBean>data;
                this.currentStatusField = JSON.parse(JSON.stringify(this.bean.status));
                this.currentStatusField.editable = false
                if( this.data.status ){
                    this.bean.status.value = this.data.status                    
                }
            });

    }

    changeStatusAction() {
        this.http.post(`achat-vente/commande`, this.bean) 
        .pipe(map((e: any) => <any>e))
        .subscribe(data => {
            this.dialogRef.close(data.record);
            this.ui.displayFlashMessage(<Array<Message>>data.messages);
        })
    }
}
