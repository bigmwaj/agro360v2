import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatTable } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { map } from 'rxjs';
import { PartnerBean, PartnerSearchBean } from 'src/app/backed/bean.core';
import { BeanList } from 'src/app/common/component/bean.list';
import { BreadcrumbItem, UIService } from 'src/app/common/service/ui.service';
import { SharedModule } from 'src/app/common/shared.module';
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
export class ListTabComponent extends BeanList<PartnerBean> implements OnInit {
    
    @Input({required:true})
    breadcrumb:BreadcrumbItem;

    searchForm: PartnerSearchBean;
    
    @Input()
    module:string

    @Input({required:true})
    editingBeans: PartnerBean[] = [];

    @Input({required:true})
    selectedTab: {index:number}

    displayedColumns: string[] = [
        'select',
        'partnerCode',
        'type',
        'status',
        'partnerName',
        'phone',
        'email',
        'city',
        'address',
        'actions'
    ];

    @ViewChild(MatTable)
    public table: MatTable<PartnerBean>;

    constructor(
        private http: HttpClient,
        public dialog: MatDialog,
        private ui: UIService
    ) {
        super()
    }

    override getViewChild(): MatTable<PartnerBean> {
        return this.table;
    }

    getKeyLabel(bean: PartnerBean): string {
        return bean.partnerCode.value;
    }
        
    ngAfterViewInit(): void {
        this.refreshPageTitle()
    }

    refreshPageTitle():void{
        this.ui.setBreadcrumb(this.breadcrumb)
    }

    ngOnInit(): void {
        this.resetSearchFormAction();
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

    resetSearchFormAction() {
        this.http
            .get("core/partner/search-form")
            .subscribe(data => {
                this.searchForm = <PartnerSearchBean>data;
                this.searchAction();
            });
    }

    searchAction() {
        let objJsonStr = JSON.stringify(this.searchForm);
        let objJsonB64 = btoa(objJsonStr);

        let queryParams = new HttpParams();
        queryParams = queryParams.append('q', objJsonB64);
        this.http
            .get("core/partner", { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.setData(data.records);
            });
    }  

    private getEditingIndex(bean:PartnerBean){
        return this.editingBeans.findIndex(e => bean.partnerCode.value == e.partnerCode.value)
    }

    addAction(bean?: PartnerBean) {   
        let queryParams = new HttpParams();
        if( bean) {
            queryParams = queryParams.append("copyFrom", bean.partnerCode.value);    
        }
        this.http.get(`core/partner/create-form`, { params: queryParams }).subscribe(data => {
            this.editingBeans.push(<PartnerBean>data); 
            this.selectedTab.index = this.editingBeans.length;
        });
    }

    editAction(bean: PartnerBean) {
        const index = this.getEditingIndex(bean);
        if( index >= 0 ){
            this.selectedTab.index = index + 1;
            return;
        }
        let queryParams = new HttpParams();
        queryParams = queryParams.append("partnerCode", bean.partnerCode.value);

        this.http.get(`core/partner/edit-form`, { params: queryParams }).subscribe(data => {
            this.editingBeans.push(<PartnerBean>data); 
            this.selectedTab.index = this.editingBeans.length;
        });
    }

    changeStatusAction(bean: PartnerBean) {
        let dialogRef = this.dialog.open(ChangeStatusDialogComponent, { data: { partnerCode: bean.partnerCode.value } });
        dialogRef.afterClosed().subscribe(result => {
            console.log(`TODO: Ne pas raffraichir si l'utilisateur n'a pas soumis le formulaire`)
            console.log(result)
            this.searchAction();
        }); 
    }

    deleteAction(bean: PartnerBean) {
        let dialogRef = this.dialog.open(DeleteDialogComponent, { data: { partnerCode: bean.partnerCode.value } });
        dialogRef.afterClosed().subscribe(result => {
            console.log(`TODO: Ne pas raffraichir si l'utilisateur n'a pas soumis le formulaire`)
            console.log(result)
            this.searchAction();
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
}
