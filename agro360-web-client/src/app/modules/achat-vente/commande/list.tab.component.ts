import { CommonModule } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTable, MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { map } from 'rxjs';
import { CommandeBean, CommandeSearchBean } from 'src/app/backed/bean.av';
import { CommandeTypeEnumVd } from 'src/app/backed/vd.av';
import { BeanList } from 'src/app/modules/common/bean.list';
import { BreadcrumbItem, UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { ChangeStatusDialogComponent } from './change-status.dialog.component';
import { DeleteDialogComponent } from './delete.dialog.component';
import { IBeanListTab } from '../../common/bean.list.tab';

@Component({
    standalone: true,
    imports: [
        CommonModule,
        MatButtonModule,
        MatDialogModule,
        MatCheckboxModule,
        MatTableModule,
        SharedModule,
        MatSidenavModule,        
        MatToolbarModule, 
        MatIconModule,
        MatTabsModule,
        MatTooltipModule
    ],
    selector: 'achat-vente-commande-list-tab',
    templateUrl: './list.tab.component.html'
})
export class ListTabComponent extends BeanList<CommandeBean> implements OnInit, IBeanListTab {
    
    @Input({required:true})
    breadcrumb:BreadcrumbItem

    @Input({required:true})
    editingBeans: CommandeBean[] = [];

    @Input({required:true})
    selectedTab: {index:number}

    @Input({required:true})
    module:string;

    partnerLabel: string;

    searchForm: CommandeSearchBean;
    
    displayedColumns: string[] = [
        'select',
        'commandeCode',
        'status',
        'date',
        'type',
        'magasin',
        'partner',
        'compte',
        'taxe',
        'remise',
        'prixTotal',
        'paiementComptant',
        'actions'
    ];

    @ViewChild(MatTable)
    public table: MatTable<CommandeBean>;
    
    constructor(
        private http: HttpClient,
        public dialog: MatDialog,
        private ui: UIService) {
        super()
    }

    getKeyLabel(bean: CommandeBean): string | number {
        return bean.commandeCode.value;
    }

    override getViewChild(): MatTable<CommandeBean> {
        return this.table;
    }
        
    ngAfterViewInit(): void {
        this.refreshPageTitle()
    }

    refreshPageTitle():void{
        this.ui.setBreadcrumb(this.breadcrumb)
    }

    ngOnInit(): void {
        this.resetSearchFormAction();
        this.breadcrumb = this.breadcrumb.addAndReturnChildItem('Liste des commandes');
        switch(this.module){
            case 'vente': this.partnerLabel = 'Client'; break;
            case 'achat': this.partnerLabel = 'Fournisseur'; break;
            default:
                throw new Error(`Aucun type de partenaire n'a été configuré pour le module ${this.module}`)
        }
    }

    resetSearchFormAction() {
        this.http
            .get(`achat-vente/commande/search-form`)
            .subscribe(data => {
                this.searchForm = <CommandeSearchBean>data;
                this.searchAction();
            });
    }

    searchAction() {
        let objJsonStr = JSON.stringify(this.searchForm);
        let objJsonB64 = btoa(objJsonStr);

        let queryParams = new HttpParams();
        queryParams = queryParams.append('q', objJsonB64);
        this.http
            .get(`achat-vente/commande`, { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.setData(data.records);
            });
    }

    addAction(bean?:CommandeBean) {    
        let queryParams = new HttpParams();  
        let type:CommandeTypeEnumVd;
        if( bean ){
            queryParams = queryParams.append("copyFrom", bean.commandeCode.value);
            type = bean.type.value
        }else{
            switch(this.module){
                case 'vente': type = CommandeTypeEnumVd.VENTE; break;
                case 'achat': type = CommandeTypeEnumVd.ACHAT; break;
                default:
                    throw new Error(`Aucun type de commande n'a été configuré pour le module ${this.module}`)
            }
        }

        queryParams = queryParams.append("type", type);
        this.http.get(`achat-vente/commande/create-form`, { params: queryParams }).subscribe(data => {
            this.editingBeans.push(<CommandeBean>data);
            this.selectedTab.index = this.editingBeans.length;
        });
    }

    private getEditingIndex(bean:CommandeBean){
        return this.editingBeans.findIndex(e => bean.commandeCode.value == e.commandeCode.value)
    }

    editAction(bean: CommandeBean) {
        const index = this.getEditingIndex(bean);
        if( index >= 0 ){
            this.selectedTab.index = index + 1;
            return;
        }

        let queryParams = new HttpParams();
        queryParams = queryParams.append("commandeCode", bean.commandeCode.value);

        this.http.get(`achat-vente/commande/edit-form`, { params: queryParams }).subscribe(data => {
            this.editingBeans.push(<CommandeBean>data); 
            this.selectedTab.index = this.editingBeans.length;
        });
    }

    changeStatusAction(bean: CommandeBean) {
        let dialogRef = this.dialog.open(ChangeStatusDialogComponent, { data: bean.commandeCode.value });
        dialogRef.afterClosed().subscribe(result => {
            console.log(`TODO: Ne pas raffraichir si l'utilisateur n'a pas soumis le formulaire`)
            console.log(result)
            this.searchAction();
        });  
    }

    deleteAction(bean: CommandeBean) {
        let dialogRef = this.dialog.open(DeleteDialogComponent, { data: bean.commandeCode.value });
        dialogRef.afterClosed().subscribe(result => {
            console.log(`TODO: Ne pas raffraichir si l'utilisateur n'a pas soumis le formulaire`)
            console.log(result)
            this.searchAction();
        });  
    }

    onDelete(bean: CommandeBean) {
        this.removeItem(bean);
    }
}
