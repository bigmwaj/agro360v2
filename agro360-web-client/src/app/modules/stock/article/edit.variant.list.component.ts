import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatTable } from '@angular/material/table';
import { map } from 'rxjs';
import { VariantBean } from 'src/app/backed/bean.stock';
import { EditActionEnumVd } from 'src/app/backed/vd.common';
import { BeanList } from 'src/app/common/bean.list';
import { SharedModule } from 'src/app/common/shared.module';

const BASE_URL = "http://localhost:8080";

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'stock-article-edit-variant-list',
    templateUrl: './edit.variant.list.component.html'
})
export class EditVariantListComponent extends BeanList<VariantBean> implements OnInit {

    @Input()
    articleCode: string;

    @Input()
    variants: Array<VariantBean>;

    @ViewChild(MatTable)
    table: MatTable<VariantBean>;

    displayedColumns: string[] = [
        'select',
        'variantCode',
        'description',
        'actions'
    ];

    constructor(private http: HttpClient) {
        super()
    }

    override getViewChild(): MatTable<VariantBean> {
        return this.table;
    }

    getKeyLabel(bean: VariantBean): string {
        return bean.variantCode.value;
    }

    ngOnInit(): void {
        this.setData(this.variants)
    }

    private __add(queryParams: HttpParams) {
        this.http
            .get(BASE_URL + "/stock/article/variant/create-form", { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.addItem(<VariantBean>data);
            });
    }

    addAction() {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('articleCode', this.articleCode);
        this.__add(queryParams);
    }

    copyAction(variant: VariantBean) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('articleCode', this.articleCode);
        queryParams = queryParams.append('copyFrom', variant.variantCode.value);

        this.__add(queryParams);
    }

    deleteAction(bean: VariantBean) {
        if( bean.action == EditActionEnumVd.CREATE ){
            this.removeItem(bean);
        }else {
            if( bean.action != EditActionEnumVd.DELETE){
                bean.action = EditActionEnumVd.DELETE;
                bean.valueChanged = true;
            }else{                
                bean.action = EditActionEnumVd.SYNC;
                bean.valueChanged = false;
            }
        }
    }
}
