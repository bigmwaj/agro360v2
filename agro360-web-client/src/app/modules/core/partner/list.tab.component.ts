import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { PartnerBean, PartnerSearchBean } from 'src/app/backed/bean.core';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanPagedListTab } from '../../common/bean.paged.list.tab';
import { IndexModalComponent as CategoryIndexModalComponent } from '../category/index.modal.component';
import { ChangeStatusDialogComponent } from './change-status.dialog.component';
import { DeleteDialogComponent } from './delete.dialog.component';

@Component({
    standalone: true,
    imports: [
        SharedModule,
        MatToolbarModule, MatIconModule
        
    ],
    selector: 'core-partner-list-tab',
    templateUrl: './list.tab.component.html'
})
export class ListTabComponent extends BeanPagedListTab<PartnerBean, PartnerSearchBean> implements OnInit {
        
    @Input()
    module:string

    displayedColumns: string[] = [
        'select',
        'partnerCode',
        'status',
        'partnerName',
        'phone',
        'email',
        'city',
        'actions'
    ];

    constructor(
        private dialog: MatDialog,
        public override http: HttpClient,       
        public override ui: UIService) {
        super(http, ui)
    }

    getKeyLabel(bean: PartnerBean): string {
        return bean.partnerCode.value;
    }

    override ngOnInit(): void {
        super.ngOnInit();
        let title;
        if( !this.module ){
            title ='Liste des partenaires'
        }else{
            switch(this.module){
                case 'vente': 
                    title= 'Liste des clients';
                    break;

                case 'achat': 
                    title= 'Liste des fournisseurs';
                    break;

                case 'paie': 
                    title= 'Liste des employés';
                    break;

                default:                         
                    title= `Liste des partenaires du module ${this.module} inconnu!'`;
                    break;
            }
        }
        this.breadcrumb = this.breadcrumb.addAndReturnChildItem(title);
    }

    areBeansEqual(b1:PartnerBean, b2:PartnerBean){
        return b1 == b2 || b1.partnerCode.value == b2.partnerCode.value;
    }

    changeStatusAction(bean: PartnerBean) {
        let dialogRef = this.dialog.open(ChangeStatusDialogComponent, { data: { partnerCode: bean.partnerCode.value } });
        dialogRef.afterClosed().subscribe(result => {
            
        }); 
    }

    deleteAction(bean: PartnerBean) {
        let dialogRef = this.dialog.open(DeleteDialogComponent, { data: { partnerCode: bean.partnerCode.value } });
        dialogRef.afterClosed().subscribe(result => {
            this.removeItem(bean)
        }); 
    }

    categoryAction() {
        let dialogRef = this.dialog.open(CategoryIndexModalComponent);
        dialogRef.afterClosed().subscribe(result => {
            console.log(`TODO: Ne pas raffraichir si l'utilisateur n'a pas soumis le formulaire`)
            console.log(result)
            this.searchAction();
        }); 
    } 

    protected override getSearchFormUrl(): string {
        return `core/partner/search-form`;
    }

    protected override getSearchUrl(): string {
        return `core/partner`;
    }

    protected override getEditFormUrl(): string {
        return `core/partner/edit-form`;
    }

    protected override getCreateFormUrl(): string {
        return `core/partner/create-form`;
    }
    
    protected override getEditQueryParam(bean: PartnerBean): HttpParams {
        let queryParams = new HttpParams();
        return queryParams.append("partnerCode", bean.partnerCode.value);
    } 
    
    protected override getCreateQueryParam(option?: any): HttpParams {
        let queryParams = new HttpParams();
        if( option?.bean) {
            queryParams = queryParams.append("copyFrom", option?.bean.partnerCode.value);    
        }

        return queryParams;
    }
}
