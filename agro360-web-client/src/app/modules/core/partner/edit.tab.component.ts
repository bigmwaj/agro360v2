import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { map } from 'rxjs';
import { PartnerBean } from 'src/app/backed/bean.core';
import { Message } from 'src/app/backed/message';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';
import { BeanTools } from 'src/app/modules/common/bean.tools';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanEditTab } from '../../common/bean.edit.tab';
import { IndexModalComponent as CategoryIndexModalComponent } from '../category/index.modal.component';
import { CategoryBlockComponent } from './category.block.component';

@Component({
    standalone: true,
    imports:[
        CategoryBlockComponent,    
        SharedModule
    ],
    selector: 'core-partner-edit-tab',
    templateUrl: './edit.tab.component.html'
})
export class EditTabComponent extends BeanEditTab<PartnerBean> implements OnInit {

    @Input()
    module:string;

    constructor(
        private http: HttpClient,
        public dialog: MatDialog,
        public override ui: UIService) {
            super(ui);
    }

    private isCreation(): boolean {
        return ClientOperationEnumVd.CREATE == this.bean.action;
    }

    ngOnInit(): void {
        let title;
        if (this.isCreation()) {            
            switch(this.module){
                case 'vente': 
                    title = `Création d'un client`;
                    break;

                case 'achat': 
                    title = `Création d'un fournisseur`;
                    break;

                case 'paie': 
                    title = `Création d'un employé`;
                    break;

                default:                         
                    title = `Création d'un partnaire`;
                    break;
            }
        } else { 
            switch(this.module){
                case 'vente': 
                    title = `Édition du client ${this.bean.partnerCode.value}`;
                    break;

                case 'achat': 
                    title = `Édition du fournisseur ${this.bean.partnerCode.value}`;
                    break;

                case 'paie': 
                    title = `Édition de l'employé ${this.bean.partnerCode.value}`;
                    break;

                default:                         
                    title = `Édition du partenaire ${this.bean.partnerCode.value}`;
                    break;
            } 
        }
        this.breadcrumb = this.breadcrumb.addAndReturnChildItem(title);
    }

    saveAction() {
        this.http.post(`core/partner`, BeanTools.reviewBeanAction(this.bean))            
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.ui.displayFlashMessage(<Array<Message>>data.messages);
                this.afterSaveAction(data.record)
            });
    }

    categoryAction() {
        this.dialog.open(CategoryIndexModalComponent);
    }
}
