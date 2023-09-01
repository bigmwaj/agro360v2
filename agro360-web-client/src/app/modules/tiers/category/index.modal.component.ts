import { NestedTreeControl } from '@angular/cdk/tree';
import { CommonModule } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatTreeModule, MatTreeNestedDataSource } from '@angular/material/tree';
import { map } from 'rxjs';
import { CategoryBean } from 'src/app/backed/bean.tiers';
import { InputEmailFieldComponent } from 'src/app/common/field/input.email';
import { InputTelFieldComponent } from 'src/app/common/field/input.tel';
import { InputTextFieldComponent } from 'src/app/common/field/input.text';
import { InputTextareaFieldComponent } from 'src/app/common/field/input.textarea';
import { SelectOneFieldComponent } from 'src/app/common/field/select.one';

const BASE_URL = "http://localhost:8080/tiers/category";

@Component({
    standalone: true,
    imports:[
        CommonModule,
        MatIconModule,             
        InputTextFieldComponent,
        InputTextareaFieldComponent,
        InputEmailFieldComponent,
        InputTelFieldComponent,
        SelectOneFieldComponent,
        MatDialogModule,
        MatTreeModule
    ],
    selector: 'tiers-category-index-modal',
    templateUrl: './index.modal.component.html'
})
export class IndexModalComponent implements OnInit {

    bean: CategoryBean;

    treeControl = new NestedTreeControl<CategoryBean>(node => node.children);

    dataSource = new MatTreeNestedDataSource<CategoryBean>();

    hasChild = (_: number, node: CategoryBean) => !!node.children && node.children.length > 0;

    constructor(private http: HttpClient) { }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('deep', 4)
        this.http
            .get(BASE_URL, { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.bean = <CategoryBean>data;
                this.dataSource.data = [this.bean]
            });
    }

}
