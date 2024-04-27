import { CommonModule } from '@angular/common';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatTableModule } from '@angular/material/table';
import { MatTabsModule } from '@angular/material/tabs';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatTooltipModule } from '@angular/material/tooltip';
import { CommandeBean, CommandeSearchBean } from 'src/app/backed/bean.av';
import { CommandeTypeEnumVd } from 'src/app/backed/vd.av';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { ChangeStatusDialogComponent } from './change-status.dialog.component';
import { DeleteDialogComponent } from './delete.dialog.component';
import { BeanPagedListTab } from '../../common/bean.paged.list.tab';

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
export class ListTabComponent extends BeanPagedListTab<CommandeBean, CommandeSearchBean> implements OnInit {
   
    @Input({required:true})
    module:string;

    partnerLabel: string;
    
    displayedColumns: string[] = [
        'select',
        'commandeCode',
        'status',
        'date',
        'type',
        'magasin',
        'partner',
        'taxe',
        'remise',
        'prixTotal',
        'cumulPaiement',
        'actions'
    ];

    constructor(
        private dialog: MatDialog,
        public override http: HttpClient,       
        public override ui: UIService) {
        super(http, ui)
    }

    getKeyLabel(bean: CommandeBean): string | number {
        return bean.commandeCode.value;
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
        this.http.get(`achat-vente/commande/create-form`, { params: queryParams })
        .subscribe(data => this.appendTab(<CommandeBean>data));
    }

    areBeansEqual(b1:CommandeBean, b2:CommandeBean){
        return b1 == b2 || b1.commandeCode.value == b2.commandeCode.value;
    }

    editAction(bean: CommandeBean) {
        if( this.isAlreadLoaded(bean) ){
            this.displayTab(bean);
            return;
        }

        let queryParams = new HttpParams();
        queryParams = queryParams.append("commandeCode", bean.commandeCode.value);

        this.http.get(`achat-vente/commande/edit-form`, { params: queryParams })
        .subscribe(data => this.appendTab(<CommandeBean>data));
    }

    changeStatusAction(bean: CommandeBean) {
        let dialogRef = this.dialog.open(ChangeStatusDialogComponent, { data: bean.commandeCode.value });
        dialogRef.afterClosed().subscribe(result => {
        });  
    }

    deleteAction(bean: CommandeBean) {
        let dialogRef = this.dialog.open(DeleteDialogComponent, { data: bean.commandeCode.value });
        dialogRef.afterClosed().subscribe(result => {
            this.removeItem(bean);
        });  
    }   

    protected override getSearchFormUrl(): string {
        return `achat-vente/commande/search-form`;
    }

    protected override getSearchUrl(): string {
        return `achat-vente/commande`;
    }
}
