import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatTable } from '@angular/material/table';
import { map } from 'rxjs';
import { CommandeBean, LigneBean, RetourLigneBean } from 'src/app/backed/bean.av';
import { Message } from 'src/app/backed/message';
import { BeanTools } from 'src/app/modules/common/bean.tools';
import { BeanList } from 'src/app/modules/common/bean.list';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'achat-vente-edit-retour-list',
    templateUrl: './edit.retour.list.component.html'
})
export class EditRetourListComponent extends BeanList<RetourLigneBean> {
    
    displayedColumns: string[] = [
        'select',
        'date',
        'status',
        'unite',
        'quantite',
        'prixUnitaire',
        'raison',
        'actions'
    ];

    constructor(
        public http: HttpClient,
        public dialog: MatDialog,
        private ui: UIService) {
        super()
    }
    
    @Input({required:true})
    public commande: CommandeBean;

    @Input({required:true})
    public ligne: LigneBean;
    
    override getKeyLabel(bean: RetourLigneBean): string | number {
        return bean.retourId.value;
    }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('commandeCode', this.commande.commandeCode.value);
        queryParams = queryParams.append('ligneId', this.ligne.ligneId.value);

        this.http
            .get(`achat-vente/commande/ligne/retour`, { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.setData(data.records);
            });
    }
  
    override setData(data: RetourLigneBean[]){
        super.setData(data);

        data.forEach(e => {

        })
    }

    addAction() {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('commandeCode', this.commande.commandeCode.value);
        queryParams = queryParams.append('ligneId', this.ligne.ligneId.value);

        this.http
            .get(`achat-vente/commande/ligne/retour/create-form`, { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.prependItem(data.record);

                if( (<Message[]>data.messages).length > 0 ){
                    this.ui.displayFlashMessage(<Message[]>data.messages);
                }
            });
    }

    saveAction() {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('commandeCode', this.commande.commandeCode.value);
        queryParams = queryParams.append('ligneId', this.ligne.ligneId.value);

        this.http.post(`achat-vente/commande/ligne/retour`, BeanTools.reviewBeansAction(this.getData()), { params: queryParams })
        .pipe(map((e: any) => <any>e))
        .subscribe(data => {
            this.dialog.closeAll();
        })
    }

}
