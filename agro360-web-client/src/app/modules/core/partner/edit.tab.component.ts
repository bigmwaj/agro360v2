import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Inject, Input, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialog } from '@angular/material/dialog';
import { IndexModalComponent as CategoryIndexModalComponent } from '../category/index.modal.component';
import { CategoryBlockComponent } from './category.block.component';
import { SharedModule } from 'src/app/common/shared.module';
import { BeanTools } from 'src/app/common/bean.tools';
import { map } from 'rxjs';
import { Message } from 'src/app/backed/message';
import { UIService } from 'src/app/common/service/ui.service';
import { PartnerBean } from 'src/app/backed/bean.core';
import { EditActionEnumVd } from 'src/app/backed/vd.common';

@Component({
    standalone: true,
    imports:[
        CategoryBlockComponent,    
        SharedModule
    ],
    selector: 'core-partner-edit-tab',
    templateUrl: './edit.tab.component.html'
})
export class EditTabComponent implements OnInit {

    @Input({required: true})
    bean: PartnerBean;

    pageTitle: string = "Edition";

    constructor(
        private http: HttpClient,
        public dialog: MatDialog,
        private ui: UIService) { }

    private isCreation(): boolean {
        return EditActionEnumVd.CREATE == this.bean.action;
    }

    ngOnInit(): void {
        if (this.isCreation()) {            
                this.ui.setTitle("Création d'un Partner")
        } else {            
            this.ui.setTitle("Édition du Partner " + this.bean.partnerCode.value)
        }
    }

    saveAction() {
        this.http.post(`core/partner`, BeanTools.reviewBeanAction(this.bean))            
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.ui.displayFlashMessage(<Array<Message>>data.messages);
            });
    }

    categoryAction() {
        this.dialog.open(CategoryIndexModalComponent);
    }
}
