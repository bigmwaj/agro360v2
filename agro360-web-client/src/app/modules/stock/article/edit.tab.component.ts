import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { map } from 'rxjs';
import { ArticleBean } from 'src/app/backed/bean.stock';
import { Message } from 'src/app/backed/message';
import { BeanTools } from 'src/app/modules/common/bean.tools';
import { BreadcrumbItem, UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { EditConversionListComponent } from './edit.conversion.list.component';
import { EditTaxeListComponent } from './edit.taxe.list.component';
import { EditVariantListComponent } from './edit.variant.list.component';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';

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

    @Input({required:true})
    breadcrumb:BreadcrumbItem

    constructor(
        private http: HttpClient,
        private ui: UIService) { }

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

    ngAfterViewInit(): void {
        this.refreshPageTitle()
    }

    refreshPageTitle():void{
        this.ui.setBreadcrumb(this.breadcrumb)
    }

    saveAction() {
        this.http.post(`stock/article`, BeanTools.reviewBeanAction(this.bean))   
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.ui.displayFlashMessage(<Array<Message>>data.messages);
            });
    }

}
