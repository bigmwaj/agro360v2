
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { map } from 'rxjs';
import { InventaireBean, InventaireSearchBean } from 'src/app/backed/bean.stock';
import { Message } from 'src/app/backed/message';
import { FieldMetadata } from 'src/app/backed/metadata';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanPagedListTab } from '../../common/bean.paged.list.tab';
import { IndexModalComponent } from '../unite/index.modal.component';
import { AjustPrixDialogComponent } from './ajust.prix.dialog.component';
import { AjustQteDialogComponent } from './ajust.qte.dialog.component';
import { CreateDialogComponent } from './create.dialog.component';

@Component({
    standalone: true,
    imports: [
        IndexModalComponent,
        SharedModule,      
        MatToolbarModule,
        FormsModule,        
        MatTooltipModule,
        MatPaginatorModule
    ],
    selector: 'stock-inventaire-list-tab',
    templateUrl: './list.tab.component.html'
})
export class ListTabComponent extends BeanPagedListTab<InventaireBean, InventaireSearchBean> implements OnInit {

    displayedColumns: string[] = [
        'select',
        'magasin',
        'article',
        'variantCode',
        'unite.achat',
        'prixUnitaireAchat',
        'unite.vente',
        'prixUnitaireVente',
        'quantite',
        'actions'
    ];

    constructor(
        private dialog: MatDialog,
        public override http: HttpClient,       
        public override ui: UIService) {
        super(http, ui)
    }

    getKeyLabel(bean: InventaireBean): string {
        return bean.article.articleCode.value;
    }

    ngOnInit(): void {
        this.resetSearchFormAction();
        this.breadcrumb = this.breadcrumb.addAndReturnChildItem('Listes des inventaires');
    }
    
    protected areBeansEqual(b1: InventaireBean, b2: InventaireBean):boolean{
        return b1 == b2 || (
            b1.magasin.magasinCode.value == b2.magasin.magasinCode.value
            && b1.article.articleCode.value == b2.article.articleCode.value
            && b1.variantCode.value == b2.variantCode.value
        );
    }
    
    ajusterQuantiteStockAction(bean: InventaireBean) {
        this.dialog.open(AjustQteDialogComponent, { data: bean });
    }

    ajusterQuantiteStockInlineAction(bean: InventaireBean) {
        console.log(bean)
        this.http.post(`stock/inventaire/ajuster-quantite`, bean)   
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.ui.displayFlashMessage(<Array<Message>>data.messages);
                bean.quantiteAjust.editable = false;
                bean.quantite.value = data.record.quantite.value;
                bean.quantiteAjust.value = 0;
            });
    }

    definirPrixVenteAction(bean: InventaireBean) {
        this.dialog.open(AjustPrixDialogComponent, { data: bean });
    }
    
    definirPrixVenteInlineAction(bean: InventaireBean) {
        this.http.post(`stock/inventaire/ajuster-prix`, bean)   
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.ui.displayFlashMessage(<Array<Message>>data.messages);
                bean.prixUnitaireVenteAjust.editable = false;
                bean.prixUnitaireVente.value = data.record.prixUnitaireVente.value;
                bean.prixUnitaireVenteAjust.value = 0;
            });
    }

    uniteAction() {
        this.dialog.open(IndexModalComponent);
    }

    addAction() {        
        this.dialog.open(CreateDialogComponent);
    }

    toggleAjustFieldAction(field:FieldMetadata<any>) {        
        field.editable = !field.editable;
    }

    protected override getSearchFormUrl(): string {
        return `stock/inventaire/search-form`;
    }

    protected override getSearchUrl(): string {
        return `stock/inventaire`;
    }
}
