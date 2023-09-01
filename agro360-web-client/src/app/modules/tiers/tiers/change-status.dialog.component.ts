import { CommonModule } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { TiersBean } from 'src/app/backed/bean.tiers';
import { InputCheckboxFieldComponent } from 'src/app/common/field/input.checkbox';
import { InputTextFieldComponent } from 'src/app/common/field/input.text';
import { SelectOneFieldComponent } from 'src/app/common/field/select.one';

const BASE_URL = "http://localhost:8080";

@Component({
    standalone: true,
    imports:[
        CommonModule,
        MatIconModule,  
        MatDialogModule,
        InputTextFieldComponent,
        SelectOneFieldComponent,
        InputCheckboxFieldComponent,
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
