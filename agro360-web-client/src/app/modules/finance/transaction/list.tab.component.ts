import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { map } from 'rxjs';
import { TransactionBean, TransactionSearchBean } from 'src/app/backed/bean.finance';
import { TransactionTypeEnumVd } from 'src/app/backed/vd.finance';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { BeanPagedListTab } from '../../common/bean.paged.list.tab';
import { IndexModalComponent as CompteIndexModalComponent } from '../compte/index.modal.component';
import { IndexModalComponent as RubriqueIndexModalComponent } from '../rubrique/index.modal.component';
import { IndexModalComponent as TaxeIndexModalComponent } from '../taxe/index.modal.component';
import { ChangeStatusDialogComponent } from './change-status.dialog.component';
import { DeleteDialogComponent } from './delete.dialog.component';
import { TransfertDialogComponent } from './transfert.dialog.component';

@Component({
    standalone: true,
    imports: [
        RubriqueIndexModalComponent,
        CompteIndexModalComponent,
        SharedModule,        
        MatToolbarModule, 
        MatIconModule,
        MatTooltipModule
    ],
    selector: 'finance-transaction-list-tab',
    templateUrl: './list.tab.component.html',
})
export class ListTabComponent extends BeanPagedListTab<TransactionBean, TransactionSearchBean> implements OnInit {

    @Input()
    module: string|null

    partnerLabel: string = "Partenaire";

    displayedColumns: string[] = [
        'select',
        'date',
        'transactionCode',
        'type',
        'rubrique',
        'status',
        'compte',
        'montant',
        'partner',
        'actions'
    ];

    constructor(
        private dialog: MatDialog,
        public override http: HttpClient,       
        public override ui: UIService) {
        super(http, ui)
    }

    getKeyLabel(bean: TransactionBean): string {
        return bean.transactionCode.value;
    }

    override ngOnInit(): void {
        super.ngOnInit();
        
        let title:string
        if( this.module != null ){
            switch(this.module){
                case "vente": 
                    title = "Encaissements";
                    this.partnerLabel = "Client";
                break;

                case "achat": 
                    title = "Paiements";
                    this.partnerLabel = "Fournisseur";
                break;

                case "paie": 
                    title = "Salaires réglés";
                    this.partnerLabel = "Employé";
                break;
                default:
                    title = `Transaction module ${this.module}`
                    this.partnerLabel = "Partenaire"
                break;
            }                    
        }else{
            title = 'Liste des transactions'
        }
        this.breadcrumb = this.breadcrumb.addAndReturnChildItem(title);
    }

    protected override initSearchQuery(queryParams: HttpParams):HttpParams{
        if( this.module != null ){
            switch(this.module){
                case "vente": 
                queryParams = queryParams.append('type', TransactionTypeEnumVd.RECETTE);
                break;

                case "achat": 
                queryParams = queryParams.append('type', TransactionTypeEnumVd.DEPENSE);
                break;

                case "paie": 
                queryParams = queryParams.append('type', TransactionTypeEnumVd.DEPENSE);
                break;
            }                    
        }
        return queryParams;
    }

    protected override initSearchForm(sf: TransactionSearchBean) : TransactionSearchBean{
        if( this.module != null ){
            
            sf.type.editable = false;

            switch(this.module){
                case "vente": 
                sf.type.value = TransactionTypeEnumVd.RECETTE;
                break;

                case "achat": 
                sf.type.value = TransactionTypeEnumVd.DEPENSE;
                break;

                case "paie": 
                sf.type.value = TransactionTypeEnumVd.DEPENSE;
                break;
            }                    
        }

        return sf;
    }

    areBeansEqual(b1:TransactionBean, b2:TransactionBean){
        return b1 == b2 || b1.transactionCode.value == b2.transactionCode.value;
    }

    deleteAction(bean: TransactionBean) {
        let dialogRef = this.dialog.open(DeleteDialogComponent, { 
            data: { 
                transactionCode:bean.transactionCode.value
            } 
        });
        dialogRef.afterClosed().subscribe(result => {
            this.removeItem(bean);
        });  
    }

    rubriqueAction() {
        this.dialog.open(RubriqueIndexModalComponent);
    }

    compteAction() {
        this.dialog.open(CompteIndexModalComponent);
    }

    taxeAction() {
        this.dialog.open(TaxeIndexModalComponent);
    }
    
    changeStatusAction(bean: TransactionBean) {
        let dialogRef = this.dialog.open(ChangeStatusDialogComponent, { data: { 
            transactionCode: bean.transactionCode.value
        }});

        dialogRef.afterClosed()
        .pipe(map((data: TransactionBean) => data)).subscribe(result => {
            this.replaceItemWith(bean, result)
        });          
    }
    
    transfertAction() {
        let dialogRef = this.dialog.open(TransfertDialogComponent);
        
        dialogRef.afterClosed().subscribe(result => { 
            if( result ){
                result.forEach((bean: TransactionBean) => {
                    this.prependItem(bean)
                });
            }           
        });  
    }

    protected override getSearchFormUrl(): string {
        return `finance/transaction/search-form`;
    }

    protected override getSearchUrl(): string {
        return `finance/transaction`;
    }

    protected override getEditFormUrl(): string {
        return `finance/transaction/edit-form`;
    }

    protected override getCreateFormUrl(): string {
        return `finance/transaction/create-form`;
    }
    
    protected override getEditQueryParam(bean: TransactionBean): HttpParams {
        let queryParams = new HttpParams();
        return queryParams.append("transactionCode", bean.transactionCode.value);
    }
    
    protected override getCreateQueryParam(option?: any): HttpParams {        
        let queryParams = new HttpParams();
        if( option?.bean) {
            queryParams = queryParams.append("copyFrom", option.bean.transactionCode.value);  
            queryParams = queryParams.append("type", option.bean.type.value);   
        }
        if( option?.type) {
            queryParams = queryParams.append("type", option.type);   
        }
        return queryParams;
    }
}
