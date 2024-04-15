import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { map } from 'rxjs';
import { ArticleBean } from 'src/app/backed/bean.stock';
import { Message } from 'src/app/backed/message';
import { BeanTools } from 'src/app/common/bean.tools';
import { UIService } from 'src/app/common/service/ui.service';
import { SharedModule } from 'src/app/common/shared.module';
import { EditConversionListComponent } from './edit.conversion.list.component';
import { EditTaxeListComponent } from './edit.taxe.list.component';
import { EditVariantListComponent } from './edit.variant.list.component';
import { EditActionEnumVd } from 'src/app/backed/vd.common';

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
export class EditTabComponent implements OnInit {

    @Input({required:true})
    bean: ArticleBean;

    constructor(
        private http: HttpClient,
        private ui: UIService) { }

    isCreation(): boolean {
        return EditActionEnumVd.CREATE == this.bean.action;
    }

    ngOnInit(): void {
        if (this.isCreation()) {
            this.ui.setTitle(`Création d'un article`)
        } else {
            this.ui.setTitle(`Édition de l'article ${this.bean.articleCode.value}`)
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
