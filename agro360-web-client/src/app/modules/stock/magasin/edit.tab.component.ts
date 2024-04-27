import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { map } from 'rxjs';
import { MagasinBean } from 'src/app/backed/bean.stock';
import { Message } from 'src/app/backed/message';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';
import { BeanTools } from 'src/app/modules/common/bean.tools';
import { BreadcrumbItem, UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanEditTab } from '../../common/bean.edit.tab';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'stock-magasin-edit-tab',
    templateUrl: './edit.tab.component.html'
})
export class EditTabComponent extends BeanEditTab<MagasinBean> implements OnInit {

    constructor(
        private http: HttpClient,
        public override ui: UIService) {
        super(ui);
    }

    isCreation(): boolean {
        return ClientOperationEnumVd.CREATE == this.bean.action;
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
