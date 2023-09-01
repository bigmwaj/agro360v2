import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MAT_DIALOG_DATA, MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatTableModule } from '@angular/material/table';
import { CaisseBean, CaisseIdBean } from 'src/app/backed/bean.stock';
import { CommonService } from 'src/app/common/service/common.service';
import { SharedModule } from 'src/app/common/shared.module';

@Component({
    standalone: true,
    imports: [
        CommonModule,
        MatButtonModule,
        MatIconModule,
        MatDialogModule,
        MatCheckboxModule,
        MatTableModule,
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
