import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { map } from 'rxjs';
import { PartnerBean } from 'src/app/backed/bean.core';
import { Message } from 'src/app/backed/message';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';

@Component({
    standalone: true,
    imports:[   
        SharedModule
    ],
    selector: 'core-partner-delete-dialog',
    templateUrl: './delete.dialog.component.html'
})
export class DeleteDialogComponent implements OnInit {

    bean: PartnerBean;

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: {partnerCode: string},
        private http: HttpClient,
        public dialog: MatDialog,
        private ui: UIService) { }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('partnerCode', this.data.partnerCode)
        this.http
            .get(`core/partner/delete-form`, { params: queryParams })
            .subscribe(data => this.bean = <PartnerBean>data);
    }

    deleteAction() {
        this.http.post(`core/partner`, this.bean)
        .pipe(map((e: any) => <any>e))
        .subscribe(data => {
            this.dialog.closeAll();
            this.ui.displayFlashMessage(<Array<Message>>data.messages);
        })
    }
}
