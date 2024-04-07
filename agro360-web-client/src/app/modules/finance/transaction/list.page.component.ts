
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
import { SharedModule } from 'src/app/common/shared.module';
import { IndexModalComponent as CompteIndexModalComponent } from '../compte/index.modal.component';
import { IndexModalComponent as RubriqueIndexModalComponent } from '../rubrique/index.modal.component';
import { ChangeStatusDialogComponent } from './change-status.dialog.component';
import { DeleteDialogComponent } from './delete.dialog.component';
import { TransfertDialogComponent } from './transfert.dialog.component';
import { UIService } from 'src/app/common/service/ui.service';

@Component({
    standalone: true,
    imports: [
        RubriqueIndexModalComponent,
        CompteIndexModalComponent,
        SharedModule,        
        MatToolbarModule, 
        MatIconModule
    ],
    selector: 'finance-transaction-list-page',
    templateUrl: './list.page.component.html',
})
export class ListPageComponent extends BeanList<TransactionBean> implements OnInit {

    searchForm: TransactionSearchBean;

    @Input()
    module: string|null

    @Input({required:true})
    editingBeans: TransactionBean[] = [];

    @Input({required:true})
    selectedTab: {index:number}

    title: string = "Liste des transactions";

    partnerLabel: string = "Partenaire";

    displayedColumns: string[] = [
        'select',
        'transactionCode',
        'type',
        'date',
        'status',
        'rubrique',
        'compte',
        'montant',
        'partner',
        'note',
        'actions'
    ];

    @ViewChild(MatTable)
    table: MatTable<TransactionBean>;

    constructor(
        public ui: UIService,
        private http: HttpClient,       
        private dialog: MatDialog) {
        super()
    }

    override getViewChild(): MatTable<TransactionBean> {
        return this.table;
    }

    getKeyLabel(bean: TransactionBean): string {
        return bean.transactionCode.value;
    }

    ngOnInit(): void {
        this.resetSearchFormAction()
        if( this.module != null ){
            switch(this.module){
                case "vente": 
                this.title = "Recettes encaissées"
                this.partnerLabel = "Client"
                break;

                case "achat": 
                this.title = "Achats réglés"
                this.partnerLabel = "Fournisseur"
                break;

                case "paie": 
                this.title = "Salaires réglés"
                this.partnerLabel = "Employé"
                break;
            }                    
        }
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
    
    addAction() {        
        this.http.get(`finance/transaction/create-form`).subscribe(data => {
            this.editingBeans.push(<TransactionBean>data); 
            this.selectedTab.index = this.editingBeans.length;
        });
    }

    copyAction(bean: TransactionBean) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append("copyFrom", bean.transactionCode.value);

        this.http.get(`finance/transaction/create-form`, { params: queryParams }).subscribe(data => {
            this.editingBeans.push(<TransactionBean>data); 
            this.selectedTab.index = this.editingBeans.length;
        });
    }

    editAction(bean: TransactionBean) {
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
        let dialogRef = this.dialog.open(RubriqueIndexModalComponent);
        dialogRef.afterClosed().subscribe(result => {
            console.log(`TODO: Ne pas raffraichir si l'utilisateur n'a pas soumis le formulaire`)
            console.log(result)
            this.searchAction();
        });  
    }

    compteAction() {
        let dialogRef =  this.dialog.open(CompteIndexModalComponent);
        dialogRef.afterClosed().subscribe(result => {
            console.log(`TODO: Ne pas raffraichir si l'utilisateur n'a pas soumis le formulaire`)
            console.log(result)
            this.searchAction();
        });  
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
            console.log(`TODO: Ne pas raffraichir si l'utilisateur n'a pas soumis le formulaire`)
            console.log(result)
            this.searchAction();
        });  
    }
}
