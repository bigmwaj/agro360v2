import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatTable } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { map } from 'rxjs';
import { TransactionBean, TransactionSearchBean } from 'src/app/backed/bean.finance';
import { TransactionTypeEnumVd } from 'src/app/backed/vd.finance';
import { BeanList } from 'src/app/common/component/bean.list';
import { BreadcrumbItem, UIService } from 'src/app/common/service/ui.service';
import { SharedModule } from 'src/app/common/shared.module';
import { IndexModalComponent as CompteIndexModalComponent } from '../compte/index.modal.component';
import { IndexModalComponent as RubriqueIndexModalComponent } from '../rubrique/index.modal.component';
import { IndexModalComponent as TaxeIndexModalComponent } from '../taxe/index.modal.component';
import { ChangeStatusDialogComponent } from './change-status.dialog.component';
import { DeleteDialogComponent } from './delete.dialog.component';
import { TransfertDialogComponent } from './transfert.dialog.component';
import { MatTooltipModule } from '@angular/material/tooltip';

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
export class ListTabComponent extends BeanList<TransactionBean> implements OnInit {

    @Input({required:true})
    breadcrumb:BreadcrumbItem

    searchForm: TransactionSearchBean;

    @Input()
    module: string|null

    @Input({required:true})
    editingBeans: TransactionBean[] = [];

    @Input({required:true})
    selectedTab: {index:number}

    partnerLabel: string = "Partenaire";

    displayedColumns: string[] = [
        'select',
        'transactionCode',
        'type',
        'rubrique',
        'date',
        'status',
        'compte',
        'montant',
        'partner',
        'actions'
    ];

    addOptions:Array<{type:TransactionTypeEnumVd; label:string}> = [
        { type:TransactionTypeEnumVd.DEPENSE, label:'Dépense'},
        { type:TransactionTypeEnumVd.RECETTE, label:'Recette'},
        { type:TransactionTypeEnumVd.DEPOT, label:'Dépot'},
        { type:TransactionTypeEnumVd.RETRAIT, label:'Retrait'},
    ]

    @ViewChild(MatTable)
    table: MatTable<TransactionBean>;

    constructor(
        public ui: UIService,
        private http: HttpClient,       
        private dialog: MatDialog) {
        super();
        
    }

    override getViewChild(): MatTable<TransactionBean> {
        return this.table;
    }

    getKeyLabel(bean: TransactionBean): string {
        return bean.transactionCode.value;
    }
        
    ngAfterViewInit(): void {
        this.refreshPageTitle()
    }

    refreshPageTitle():void{
        this.ui.setBreadcrumb(this.breadcrumb)
    }

    ngOnInit(): void {
        this.resetSearchFormAction()
        let title:string
        if( this.module != null ){
            switch(this.module){
                case "vente": 
                    title = "Encaissements";
                    this.partnerLabel = "Client";
                    this.addOptions = [
                        { type:TransactionTypeEnumVd.RECETTE, label:'Recette'}
                    ]
                break;

                case "achat": 
                    title = "Paiements";
                    this.partnerLabel = "Fournisseur";this.addOptions = [
                        { type:TransactionTypeEnumVd.DEPENSE, label:'Dépense'},
                    ]
                break;

                case "paie": 
                    title = "Salaires réglés";
                    this.partnerLabel = "Employé";this.addOptions = [
                        { type:TransactionTypeEnumVd.DEPENSE, label:'Dépense'},
                    ]
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

    resetSearchFormAction() {
        this.http        
            .get(`finance/transaction/search-form`)  
            .subscribe(data => {
                this.searchForm = <TransactionSearchBean>data;
                if( this.module != null ){
                    this.searchForm.type.editable = false;

                    switch(this.module){
                        case "vente": 
                        this.searchForm.type.value = TransactionTypeEnumVd.RECETTE;
                        break;

                        case "achat": 
                        this.searchForm.type.value = TransactionTypeEnumVd.DEPENSE;
                        break;

                        case "paie": 
                        this.searchForm.type.value = TransactionTypeEnumVd.DEPENSE;
                        break;
                    }                    
                }
                this.searchAction();
            });
    }

    searchAction() {
        let objJsonStr = JSON.stringify(this.searchForm);
        let objJsonB64 = btoa(objJsonStr);

        let queryParams = new HttpParams();
        queryParams = queryParams.append('q', objJsonB64);
        this.http
            .get(`finance/transaction`, { params: queryParams })  
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.setData(data.records);
            });
    }
    
    addAction(type:TransactionTypeEnumVd, bean?: TransactionBean) {   
        let queryParams = new HttpParams();
        queryParams = queryParams.append("type", type);
        if( bean ){
            queryParams = queryParams.append("copyFrom", bean.transactionCode.value);
        }

        this.http.get(`finance/transaction/create-form`, { params: queryParams }).subscribe(data => {
            this.editingBeans.push(<TransactionBean>data); 
            this.selectedTab.index = this.editingBeans.length;
        });
    }
    
    copyAction(bean: TransactionBean) {   
        this.addAction(bean.type.value, bean)
    }

    private getEditingIndex(bean:TransactionBean){
        return this.editingBeans.findIndex(e => bean.transactionCode.value == e.transactionCode.value)
    }

    editAction(bean: TransactionBean) {
        const index = this.getEditingIndex(bean);
        if( index >= 0 ){
            this.selectedTab.index = index + 1;
            return;
        }
        let queryParams = new HttpParams();
        queryParams = queryParams.append("transactionCode", bean.transactionCode.value);

        this.http.get(`finance/transaction/edit-form`, { params: queryParams }).subscribe(data => {
            this.editingBeans.push(<TransactionBean>data); 
            this.selectedTab.index = this.editingBeans.length;
        });
    }

    deleteAction(bean: TransactionBean) {
        let dialogRef = this.dialog.open(DeleteDialogComponent, { 
            data: { 
                transactionCode:bean.transactionCode.value
            } 
        });
        dialogRef.afterClosed().subscribe(result => {
            console.log(`TODO: Ne pas raffraichir si l'utilisateur n'a pas soumis le formulaire`)
            console.log(result)
            this.searchAction();
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

        dialogRef.afterClosed().subscribe(result => {
            console.log(`TODO: Ne pas raffraichir si l'utilisateur n'a pas soumis le formulaire`)
            console.log(result)
            this.searchAction();
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
}
