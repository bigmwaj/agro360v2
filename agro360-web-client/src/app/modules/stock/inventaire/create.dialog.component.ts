import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { map } from 'rxjs';
import { InventaireBean, VariantBean } from 'src/app/backed/bean.stock';
import { Message } from 'src/app/backed/message';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { StockService } from '../stock.service';
import { EditVariantListComponent } from './edit.variant.list.component';

@Component({
    standalone: true,
    imports: [
        SharedModule,    
        EditVariantListComponent    
    ],
    selector: 'stock-inventaire-create-dialog',
    templateUrl: './create.dialog.component.html'
})
export class CreateDialogComponent implements OnInit {

    bean: InventaireBean;

    variants: Array<VariantBean>

    @ViewChild(EditVariantListComponent)
    variantsTable: EditVariantListComponent;

    constructor(
        private http: HttpClient,
        private ui: UIService,
        private service: StockService) {            
        
    }
    
    ngOnInit(): void {
        this.http
            .get(`stock/inventaire/create-form`)    
            .subscribe(data => this.bean = <InventaireBean>data);
    }

    createAction() {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('magasinCode', this.bean.magasin.magasinCode.value);
        queryParams = queryParams.append('articleCode', this.bean.article.articleCode.value);

        this.http.post(`stock/inventaire`, this.variants, {params: queryParams})   
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.ui.displayFlashMessage(<Array<Message>>data.messages);
                this.onArticleChanged(this.bean)
            });
    }

    onMagasinChanged(bean: InventaireBean){        
        let queryParams = new HttpParams();
        queryParams = queryParams.append('magasinCode', bean.magasin.magasinCode.value);
        this.service.getStockableArticlesAsValueOptions(
            this.http, queryParams
        ).subscribe(e => {
            this.bean.article.articleCode.value = '';
            this.bean.article.articleCode.valueOptions = e;
            this.variants = []
            this.variantsTable.setData([])
        });
    }

    onArticleChanged(bean: InventaireBean){
        let queryParams = new HttpParams();
        queryParams = queryParams.append('magasinCode', bean.magasin.magasinCode.value);
        queryParams = queryParams.append('articleCode', bean.article.articleCode.value);
        this.http
            .get(`stock/inventaire/non-stocked-article-variants`, { params: queryParams })  
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.variants = data.records;
                this.variantsTable.setData(data.records)
            });
    }
}
