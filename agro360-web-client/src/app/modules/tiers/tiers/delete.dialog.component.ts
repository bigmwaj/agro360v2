import { CommonModule } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { TiersBean } from 'src/app/backed/bean.tiers';
import { CommonService } from 'src/app/common/service/common.service';

@Component({
    standalone: true,
    imports:[
        CommonModule,
        MatIconModule,  
        MatDialogModule 
    ],
    selector: 'tiers-tiers-delete-dialog',
    templateUrl: './delete.dialog.component.html'
})
export class DeleteDialogComponent implements OnInit {

    bean: TiersBean;

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: {tiersCode: string},
        private http: HttpClient,
        private commonService: CommonService,
        public dialog: MatDialog) { }

    ngOnInit(): void {
        let queryParams = this.commonService.encodeQuery(this.data);
        this.http
            .get(`${this.commonService.getBackendUrl('tiers/tiers/delete-form')}`, { params: queryParams })
            .subscribe(data => this.bean = <TiersBean>data);
    }

    deleteAction() {
        this.http.post(`${this.commonService.getBackendUrl('tiers/tiers')}`, this.bean).subscribe(data => console.log(data))
    }
}
