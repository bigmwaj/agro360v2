import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTable } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { map } from 'rxjs';
import { PartnerBean, PartnerSearchBean } from 'src/app/backed/bean.core';
import { BeanList } from 'src/app/common/component/bean.list';
import { IndexModalComponent as CategoryIndexModalComponent } from '../category/index.modal.component';
import { ChangeStatusDialogComponent } from './change-status.dialog.component';
import { DeleteDialogComponent } from './delete.dialog.component';
import { SharedModule } from 'src/app/common/shared.module';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';

@Component({
    standalone: true,
    imports: [
        SharedModule,
        MatToolbarModule, MatIconModule
        
    ],
    selector: 'core-partner-list-page',
    templateUrl: './list.page.component.html'
})
export class ListPageComponent extends BeanList<PartnerBean> implements OnInit {

    searchForm: PartnerSearchBean;

    @Input({required:true})
    editingBeans: PartnerBean[] = [];

    @Input({required:true})
    selectedTab: {index:number}

    displayedColumns: string[] = [
        'select',
        'partnerCode',
        'partnerType',
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
        public dialog: MatDialog
    ) {
        super()
    }

    override getViewChild(): MatTable<PartnerBean> {
        return this.table;
    }

    getKeyLabel(bean: PartnerBean): string {
        return bean.partnerCode.value;
    }

    ngOnInit(): void {
        this.resetSearchFormAction()
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

    addAction() {        
        this.http.get(`core/partner/create-form`).subscribe(data => {
            this.editingBeans.push(<PartnerBean>data); 
            this.selectedTab.index = this.editingBeans.length;
        });
    }

    copyAction(bean: PartnerBean) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("copyFrom", bean.partnerCode.value);

        this.http.get(`core/partner/create-form`, { params: queryParams }).subscribe(data => {
            this.editingBeans.push(<PartnerBean>data); 
            this.selectedTab.index = this.editingBeans.length;
        });
    }

    editAction(bean: PartnerBean) {
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
