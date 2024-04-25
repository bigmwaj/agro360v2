import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { map } from 'rxjs';
import { MagasinBean } from 'src/app/backed/bean.stock';
import { Message } from 'src/app/backed/message';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';
import { BeanTools } from 'src/app/modules/common/bean.tools';
import { BreadcrumbItem, UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { EditCasierListComponent } from './edit.casier.list.component';

@Component({
    standalone: true,
    imports: [
        SharedModule,
        EditCasierListComponent
    ],
    selector: 'stock-magasin-edit-tab',
    templateUrl: './edit.tab.component.html'
})
export class EditTabComponent implements OnInit {

    @Input({required:true})
    bean: MagasinBean;

    @Input({required:true})
    breadcrumb:BreadcrumbItem;

    constructor(
        private http: HttpClient,
        private ui: UIService) { }

    isCreation(): boolean {
        return ClientOperationEnumVd.CREATE == this.bean.action;
    }

    ngAfterViewInit(): void {
        this.refreshPageTitle()
    }

    refreshPageTitle():void{
        this.ui.setBreadcrumb(this.breadcrumb)
    }
    
    ngOnInit(): void {
        if (this.isCreation()) {
            this.breadcrumb = this.breadcrumb.addAndReturnChildItem(`Création d'un magasin`);
        } else {
            this.breadcrumb = this.breadcrumb.addAndReturnChildItem(`Édition du magasin ${this.bean.magasinCode.value}`);
        }
    }

    saveAction() {
        this.http.post(`stock/magasin`, BeanTools.reviewBeanAction(this.bean))
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.ui.displayFlashMessage(<Array<Message>>data.messages);
            });
    }
}
