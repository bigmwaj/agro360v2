import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { map } from 'rxjs';
import { InventaireBean } from 'src/app/backed/bean.stock';
import { Message } from 'src/app/backed/message';
import { BeanTools } from 'src/app/common/bean.tools';
import { UIService } from 'src/app/common/service/ui.service';
import { SharedModule } from 'src/app/common/shared.module';
import { EditActionEnumVd } from 'src/app/backed/vd.common';

@Component({
    standalone: true,
    imports: [
        SharedModule,
    ],
    selector: 'stock-inventaire-edit-tab',
    templateUrl: './edit.tab.component.html'
})
export class EditTabComponent implements OnInit {

    @Input({required:true})
    bean: InventaireBean;

    constructor(
        private http: HttpClient,
        private ui: UIService) { }

    isCreation(): boolean {
        return EditActionEnumVd.CREATE == this.bean.action;
    }

    ngOnInit(): void {
        if (this.isCreation()) {
            this.ui.setTitle(`Création d'un inventaire`)
        } else {
            this.ui.setTitle(`Édition de l'inventaire ${this.bean.article.articleCode.value}`)
        }
    }

    saveAction() {
        console.log(this.bean)
        this.http.post(`stock/inventaire`, BeanTools.reviewBeanAction(this.bean))   
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.ui.displayFlashMessage(<Array<Message>>data.messages);
            });
    }

}
