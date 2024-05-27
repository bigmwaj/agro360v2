import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatTable } from '@angular/material/table';
import { ConversionBean } from 'src/app/backed/bean.stock';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';
import { BeanList } from 'src/app/modules/common/bean/bean.list';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { map } from 'rxjs';

@Component({
    standalone: true,
    imports: [
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

    displayedColumns: string[] = [
        'unite',
        'facteur',
        'actions'
    ];

    constructor(
        private http: HttpClient) {
        super()
    }

    ngOnInit(): void {
        this.setData(this.conversions)
    }

    private __add(queryParams: HttpParams) {
        this.http        
            .get(`stock/article/conversion/create-form`, { params: queryParams })  
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

    deleteAction(bean: ConversionBean) {
        if( bean.action == ClientOperationEnumVd.CREATE ){
            this.removeItem(bean);
        }else {
            if( bean.action != ClientOperationEnumVd.DELETE ){
                bean.action = ClientOperationEnumVd.DELETE;
                bean.valueChanged = true;
            }else{                
                bean.action = ClientOperationEnumVd.SYNC;
                bean.valueChanged = false;
            }
        }
    }
}
