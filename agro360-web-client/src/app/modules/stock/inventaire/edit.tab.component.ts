import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { InventaireBean } from 'src/app/backed/bean.stock';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanEditTab } from '../../common/bean/bean.edit.tab';

@Component({
    standalone: true,
    imports: [
        SharedModule,
    ],
    selector: 'stock-inventaire-edit-tab',
    templateUrl: './edit.tab.component.html'
})
export class EditTabComponent extends BeanEditTab<InventaireBean> implements OnInit {

    constructor(
        public override http: HttpClient,
        public override ui: UIService) {
        super(ui, http);
    }

    ngOnInit(): void {
       
    }

    saveUrl():string {
        return `stock/inventaire`;
    }

}
