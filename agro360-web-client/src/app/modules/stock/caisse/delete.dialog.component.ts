import { HttpClient } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { CaisseBean, CaisseIdBean } from 'src/app/backed/bean.stock';
import { CommonService } from 'src/app/common/service/common.service';
import { SharedModule } from 'src/app/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'stock-caisse-delete-dialog',
    templateUrl: './delete.dialog.component.html'
})
export class DeleteDialogComponent implements OnInit {

    bean: CaisseBean;

    constructor(
        @Inject(MAT_DIALOG_DATA) public data: CaisseIdBean,
        private commonService: CommonService,
        private http: HttpClient,
        public dialog: MatDialog) { }

    ngOnInit(): void {
        const queryParams = this.commonService.encodeQuery(this.data);
        this.http
            .get(`${this.commonService.getBackendUrl('stock/caisse/delete-form')}`, { params: queryParams })
            .subscribe(data => this.bean = <CaisseBean>data);
    }

    deleteAction() {
        this.http.post(`${this.commonService.getBackendUrl('stock/caisse')}`, this.bean).subscribe(data => console.log(data))
    }
}
