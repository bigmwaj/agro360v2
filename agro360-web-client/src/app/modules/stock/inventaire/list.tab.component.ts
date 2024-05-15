
import { HttpClient, HttpParams } from '@angular/common/http';
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
import { BeanPagedListTab } from '../../common/bean/bean.paged.list.tab';
import { IndexModalComponent } from '../unite/index.modal.component';
import { EditDialogComponent } from './edit.dialog.component';
import { CreateDialogComponent } from './create.dialog.component';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';
import { UploadDialogComponent } from '../../common/component/upload.dialog.component';

@Component({
    standalone: true,
    imports: [
        IndexModalComponent,
        SharedModule,      
        MatToolbarModule,
        FormsModule,        
        MatTooltipModule,
        MatPaginatorModule,
        UploadDialogComponent
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

    override ngOnInit(): void {
        super.ngOnInit();
        this.breadcrumb = this.breadcrumb.addAndReturnChildItem('Listes des inventaires');
    }
    
    protected areBeansEqual(b1: InventaireBean, b2: InventaireBean):boolean{
        return b1 == b2 || (
            b1.magasin.magasinCode.value == b2.magasin.magasinCode.value
            && b1.article.articleCode.value == b2.article.articleCode.value
            && b1.variantCode.value == b2.variantCode.value
        );
    }
    
    private ajusterAction(bean: InventaireBean, operation: ClientOperationEnumVd) {
        const dialogRef = this.dialog.open(EditDialogComponent, { data: {bean:bean, operation:operation} });
        dialogRef.afterClosed().subscribe(result => {
            if( result ){
                this.replaceItemWith(bean, result);
            }
        });  
    }
    
    ajusterQuantiteAction(bean: InventaireBean) {
        this.ajusterAction(bean, ClientOperationEnumVd.ACT01);
    }
    
    ajusterPrixVenteAction(bean: InventaireBean) {
        this.ajusterAction(bean, ClientOperationEnumVd.ACT02);
    }

    ajusterUniteVenteAction(bean: InventaireBean) {
        this.ajusterAction(bean, ClientOperationEnumVd.ACT03);
    }

    ajusterUniteAchatAction(bean: InventaireBean) {
        this.ajusterAction(bean, ClientOperationEnumVd.ACT04);
    }

    private ajusterInline(bean: InventaireBean) {
        this.http.post(`stock/inventaire/ajuster`, bean)   
            .pipe(map((e: any) => <any>e))
            .subscribe(data => {
                this.ui.displayFlashMessage(<Array<Message>>data.messages);
                this.replaceItemWith(bean, data.record);
            });
    }

    ajusterQuantiteStockInlineAction(bean: InventaireBean) {
        bean.action = ClientOperationEnumVd.ACT01;
        bean.quantite.value = bean.quantiteAjust.value;
        this.ajusterInline(bean)
    }
    
    definirPrixVenteInlineAction(bean: InventaireBean) {
        bean.action = ClientOperationEnumVd.ACT02;
        bean.prixUnitaireVente.value = bean.prixUnitaireVenteAjust.value
        this.ajusterInline(bean)
    }

    uniteAction() {
        this.dialog.open(IndexModalComponent);
    }

    importAction() {
        this.dialog.open(UploadDialogComponent);
    }

    override addAction(option?:any) {        
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

    protected override getEditFormUrl(): string {
        return `stock/inventaire/edit-form`;
    }

    protected override getCreateFormUrl(): string {
        return `stock/inventaire/create-form`;
    }

    protected override getEditQueryParam(bean: InventaireBean): HttpParams {
        throw Error('Not implemented!');
    }
    
    protected override getCreateQueryParam(bean: InventaireBean): HttpParams {
        throw Error('Not implemented!');
    }
}
