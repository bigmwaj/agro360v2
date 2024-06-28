import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ArticleBean } from 'src/app/backed/bean.stock';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanEditTab } from '../../common/bean/bean.edit.tab';
import { EditConversionListComponent } from './edit.conversion.list.component';
import { EditTaxeListComponent } from './edit.taxe.list.component';
import { EditVariantListComponent } from './edit.variant.list.component';
import { AutocompleteConfig } from '../../common/field/autocomplete';
import { PartnerBean } from 'src/app/backed/bean.core';
import { CategoryBlockComponent } from '../category/category.block.component';

@Component({
    standalone: true,
    imports: [
        EditConversionListComponent,
        EditVariantListComponent,
        EditTaxeListComponent,
        CategoryBlockComponent,
        SharedModule
    ],
    selector: 'stock-article-edit-tab',
    templateUrl: './edit.tab.component.html'
})
export class EditTabComponent extends BeanEditTab<ArticleBean> implements OnInit {

    partnerLookupConfig:AutocompleteConfig;

    constructor(
        public override http: HttpClient,
        public override ui: UIService) { 
        super(ui, http)
    }

    ngOnInit(): void {
        if ( this.isCreation() ) {
            this.breadcrumb = this.breadcrumb.addAndReturnChildItem(`Création d'un article`);
        } else {
            this.breadcrumb = this.breadcrumb.addAndReturnChildItem(`Édition de l'article ${this.bean.articleCode.value}`);
        }

        this.setupPartnerLookupConfig();
    }

    override saveUrl(): string {
        return `stock/article`;
    }
    
    private setupPartnerLookupConfig(){
        const http = this.http;
        this.partnerLookupConfig =  {

            displayFn:function(bean: PartnerBean){
                return bean.partnerCode.value + ' - ' + bean.partnerName.value;
            },
    
            keyFn:function(bean: PartnerBean){
                return bean.partnerCode.value;
            },
    
            lookupFn: function(q:string){                
                let queryParams = new HttpParams();
                queryParams = queryParams.append('query', q);
                return http.get(`stock/article/partner-query`, { params: queryParams });
            }    
        }
    }
}
