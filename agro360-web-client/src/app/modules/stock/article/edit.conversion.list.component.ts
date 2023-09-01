import { CommonModule } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatTable, MatTableModule } from '@angular/material/table';
import { map } from 'rxjs';
import { ConversionBean } from 'src/app/backed/bean.stock';
import { EditActionEnumVd } from 'src/app/backed/vd.common';
import { BeanList } from 'src/app/common/bean.list';
import { SharedModule } from 'src/app/common/shared.module';

const BASE_URL = "http://localhost:8080";

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
    selector: 'stock-article-edit-conversion-list',
    templateUrl: './edit.conversion.list.component.html'
})
export class EditConversionListComponent extends BeanList<ConversionBean> implements OnInit {

    getKeyLabel(bean: ConversionBean): string {
        return bean.unite.uniteCode.value;
    }

    @Input()
    articleCode: string;

    @Input()
    conversions: Array<ConversionBean>;

    @ViewChild(MatTable)
    table: MatTable<ConversionBean>;

    displayedColumns: string[] = [
        'select',
        'unite',
        'facteur',
        'actions'
    ];

    constructor(private http: HttpClient) {
        super()
    }

    override getViewChild(): MatTable<ConversionBean> {
        return this.table;
    }

    ngOnInit(): void {
        this.setData(this.conversions)
    }

    private __add(queryParams: HttpParams) {
        this.http
            .get(BASE_URL + "/stock/article/conversion/create-form", { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.addItem(<ConversionBean>data);
            });
    }

    addAction() {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('articleCode', this.articleCode);

        this.__add(queryParams);
    }

    copyAction(conversion: ConversionBean) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('articleCode', this.articleCode);
        queryParams = queryParams.append('copyFrom', conversion.unite.uniteCode.value);

        this.__add(queryParams);
    }

    deleteAction(conversion: ConversionBean) {
        conversion.action = EditActionEnumVd.DELETE;
    }
}
