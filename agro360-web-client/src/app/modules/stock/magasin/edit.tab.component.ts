import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { MagasinBean } from 'src/app/backed/bean.stock';
import { SharedModule } from 'src/app/common/shared.module';
import { EditCasierListComponent } from './edit.casier.list.component';
import { BeanTools } from 'src/app/common/bean.tools';
import { map } from 'rxjs';
import { Message } from 'src/app/backed/message';
import { UIService } from 'src/app/common/service/ui.service';
import { EditActionEnumVd } from 'src/app/backed/vd.common';

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

    constructor(
        private http: HttpClient,
        private ui: UIService) { }

    isCreation(): boolean {
        return EditActionEnumVd.CREATE == this.bean.action;
    }
    
    ngOnInit(): void {
        if (this.isCreation()) {
            this.ui.setTitle(`Création d'un magasin`)
        } else {
            this.ui.setTitle(`Édition du magasin ${this.bean.magasinCode.value}`)
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
