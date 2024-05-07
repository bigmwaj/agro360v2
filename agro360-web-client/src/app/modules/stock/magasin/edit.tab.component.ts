import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MagasinBean } from 'src/app/backed/bean.stock';
import { UIService } from 'src/app/modules/common/service/ui.service';
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
        public override http: HttpClient,
        public override ui: UIService) {
        super(ui, http);
    }
    
    ngOnInit(): void {
        if (this.isCreation()) {
            this.breadcrumb = this.breadcrumb.addAndReturnChildItem(`Création d'un magasin`);
        } else {
            this.breadcrumb = this.breadcrumb.addAndReturnChildItem(`Édition du magasin ${this.bean.magasinCode.value}`);
        }
    }

    saveUrl():string {
        return `stock/magasin`;
    }
}
