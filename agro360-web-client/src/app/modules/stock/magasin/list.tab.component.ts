import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTable } from '@angular/material/table';
import { map } from 'rxjs';
import { MagasinBean, MagasinSearchBean } from 'src/app/backed/bean.stock';
import { BeanList } from 'src/app/modules/common/bean.list';
import { BreadcrumbItem, UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { IndexModalComponent } from '../unite/index.modal.component';
import { MatToolbarModule } from '@angular/material/toolbar';

@Component({
    standalone: true,
    imports: [
        IndexModalComponent,
        SharedModule,           
        MatToolbarModule, 
    ],
    selector: 'stock-magasin-list-tab',
    templateUrl: './list.tab.component.html'
})
export class ListTabComponent extends BeanList<MagasinBean> implements OnInit {

    @Input({required:true})
    editingBeans: MagasinBean[] = [];

    @Input({required:true})
    selectedTab: {index:number}

    @Input({required:true})
    breadcrumb:BreadcrumbItem

    searchForm: MagasinSearchBean;

    @ViewChild(MatTable)
    table: MatTable<MagasinBean>;

    displayedColumns: string[] = [
        'select',
        'magasinCode',
        'description',
        'actions'
    ];

    constructor(
        private http: HttpClient,
        public dialog: MatDialog,
        private ui: UIService) {
            super()
    }

    override getViewChild(): MatTable<MagasinBean> {
        return this.table;
    }

    getKeyLabel(bean: MagasinBean): string {
        return bean.magasinCode.value;
    }

    ngAfterViewInit(): void {
        this.refreshPageTitle()
    }

    refreshPageTitle():void{
        this.ui.setBreadcrumb(this.breadcrumb)
    }

    ngOnInit(): void {
        this.resetSearchFormAction();
        this.breadcrumb = this.breadcrumb.addAndReturnChildItem('Liste des magasins');
    }

    resetSearchFormAction() {
        this.http
            .get("stock/magasin/search-form")
            .subscribe(data => {
                this.searchForm = <MagasinSearchBean>data;
                this.searchAction();
            });
    }

    searchAction() {
        let objJsonStr = JSON.stringify(this.searchForm);
        let objJsonB64 = btoa(objJsonStr);

        let queryParams = new HttpParams();
        queryParams = queryParams.append('q', objJsonB64);
        this.http
            .get("stock/magasin", { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.setData(data.records);
            });
    }

    addAction(bean?: MagasinBean) { 
        let queryParams = new HttpParams();
        if( bean ){
            queryParams = queryParams.append("copyFrom", bean.magasinCode.value);       
        }
        this.http.get(`stock/magasin/create-form`, { params: queryParams }).subscribe(data => {
            this.editingBeans.push(<MagasinBean>data); 
            this.selectedTab.index = this.editingBeans.length;
        });
    }

    private getEditingIndex(bean:MagasinBean){
        return this.editingBeans.findIndex(e => bean.magasinCode.value == e.magasinCode.value)
    }

    editAction(bean: MagasinBean) {
        const index = this.getEditingIndex(bean);
        if( index >= 0 ){
            this.selectedTab.index = index + 1;
            return;
        }
        let queryParams = new HttpParams();
        queryParams = queryParams.append("magasinCode", bean.magasinCode.value);

        this.http.get(`stock/magasin/edit-form`, { params: queryParams }).subscribe(data => {
            this.editingBeans.push(<MagasinBean>data); 
            this.selectedTab.index = this.editingBeans.length;
        });
    }

    deleteAction(bean: MagasinBean) {
        // Open Dialog
    }

    uniteAction() {
        this.dialog.open(IndexModalComponent);
    }
}
