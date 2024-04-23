
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTable } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { map } from 'rxjs';
import { InventaireBean, InventaireSearchBean } from 'src/app/backed/bean.stock';
import { BeanList } from 'src/app/common/component/bean.list';
import { BreadcrumbItem, UIService } from 'src/app/common/service/ui.service';
import { SharedModule } from 'src/app/common/shared.module';
import { IndexModalComponent } from '../unite/index.modal.component';
import { AjustPrixDialogComponent } from './ajust.prix.dialog.component';
import { AjustQteDialogComponent } from './ajust.qte.dialog.component';
import { CreateDialogComponent } from './create.dialog.component';

@Component({
    standalone: true,
    imports: [
        IndexModalComponent,
        SharedModule,      
        MatToolbarModule 
    ],
    selector: 'stock-inventaire-list-tab',
    templateUrl: './list.tab.component.html'
})
export class ListTabComponent extends BeanList<InventaireBean> implements OnInit {

    @Input({required:true})
    editingBeans: InventaireBean[] = [];

    @Input({required:true})
    selectedTab: {index:number}

    @Input({required:true})
    breadcrumb:BreadcrumbItem

    searchForm: InventaireSearchBean;

    displayedColumns: string[] = [
        'select',
        'magasin',
        'article',
        'variantCode',
        'article.unite',
        'quantite',
        'prixUnitaireAchat',
        'prixUnitaireVente',
        'actions'
    ];

    @ViewChild(MatTable)
    table: MatTable<InventaireBean>;

    constructor(
        private http: HttpClient,       
        private dialog: MatDialog,
        private ui: UIService) {
        super()
    }

    override getViewChild(): MatTable<InventaireBean> {
        return this.table;
    }

    getKeyLabel(bean: InventaireBean): string {
        return bean.article.articleCode.value;
    }

    ngOnInit(): void {
        this.resetSearchFormAction();
        this.breadcrumb = this.breadcrumb.addAndReturnChildItem('Listes des inventaires');
    }

    ngAfterViewInit(): void {
        this.refreshPageTitle()
    }

    refreshPageTitle():void{
        this.ui.setBreadcrumb(this.breadcrumb)
    }

    resetSearchFormAction() {
        this.http        
            .get(`stock/inventaire/search-form`)  
            .subscribe(data => {
                this.searchForm = <InventaireSearchBean>data;
                this.searchAction();
            });
    }

    searchAction() {
        let objJsonStr = JSON.stringify(this.searchForm);
        let objJsonB64 = btoa(objJsonStr);

        let queryParams = new HttpParams();
        queryParams = queryParams.append('q', objJsonB64);
        this.http
            .get(`stock/inventaire`, { params: queryParams })  
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.setData(data.records);
            });
    }

    ajusterQuantiteStockAction(bean: InventaireBean) {
        this.dialog.open(AjustQteDialogComponent, { data: bean });
    }

    definirPrixVenteAction(bean: InventaireBean) {
        this.dialog.open(AjustPrixDialogComponent, { data: bean });
    }

    uniteAction() {
        this.dialog.open(IndexModalComponent);
    }

    addAction() {        
        this.dialog.open(CreateDialogComponent);
    }
}
