import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { map } from 'rxjs';
import { ArticleBean } from 'src/app/backed/bean.stock';
import { Message } from 'src/app/backed/message';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';
import { BeanTools } from 'src/app/modules/common/bean.tools';
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
        private http: HttpClient,
        public override ui: UIService) { 
        super(ui)
    }

    isCreation(): boolean {
        return ClientOperationEnumVd.CREATE == this.bean.action;
    }

    ngOnInit(): void {
        if ( this.isCreation() ) {
            this.breadcrumb = this.breadcrumb.addAndReturnChildItem(`Création d'un article`);
        } else {
            this.breadcrumb = this.breadcrumb.addAndReturnChildItem(`Édition de l'article ${this.bean.articleCode.value}`);
        }
    }

    saveAction() {
        this.http.post(`stock/article`, BeanTools.reviewBeanAction(this.bean))   
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.ui.displayFlashMessage(<Array<Message>>data.messages);
            });
    }

}
