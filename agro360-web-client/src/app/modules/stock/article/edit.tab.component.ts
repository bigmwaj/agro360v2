import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ArticleBean } from 'src/app/backed/bean.stock';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanEditTab } from '../../common/bean.edit.tab';
import { EditConversionListComponent } from './edit.conversion.list.component';
import { EditTaxeListComponent } from './edit.taxe.list.component';
import { EditVariantListComponent } from './edit.variant.list.component';

@Component({
    standalone: true,
    imports: [
        EditConversionListComponent,
        EditVariantListComponent,
        EditTaxeListComponent,
        SharedModule
    ],
    selector: 'stock-article-edit-tab',
    templateUrl: './edit.tab.component.html'
})
export class EditTabComponent extends BeanEditTab<ArticleBean> implements OnInit {

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
    }

    override saveUrl(): string {
        return `stock/article`;
    }
}
