import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { map } from 'rxjs';
import { CommandeBean, ReceptionLigneBean } from 'src/app/backed/bean.av';
import { Message } from 'src/app/backed/message';
import { BeanList } from 'src/app/modules/common/bean/bean.list';
import { BeanTools } from 'src/app/modules/common/bean/bean.tools';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'achat-vente-reception-list',
    templateUrl: './reception.list.component.html'
})
export class ReceptionListComponent extends BeanList<ReceptionLigneBean> {
    
    displayedColumns: string[] = [
        'article',
        'status',
        'date',
        'unite.commande',
        'unite',
        'quantite.commande',
        'quantite',
        'prixUnitaire.commande',
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
    public bean: CommandeBean;
    
    override getKeyLabel(bean: ReceptionLigneBean): string | number {
        return bean.receptionId.value;
    }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('commandeCode', this.bean.commandeCode.value);

        this.http
            .get(`achat-vente/commande/reception`, { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.setData(data.records);
            });
    }
  
    override setData(data: ReceptionLigneBean[]){
        super.setData(data);
    }

    addAction() {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('commandeCode', this.bean.commandeCode.value);

        this.http
            .get(`achat-vente/commande/reception/create-form`, { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.prependItem(data.record);
            });
    }

    saveAction() {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('commandeCode', this.bean.commandeCode.value);

        this.http.post(`achat-vente/commande/reception`, BeanTools.reviewBeansAction(this.getData()), { params: queryParams })
        .pipe(map((e: any) => <any>e))
        .subscribe(data => {
            this.dialog.closeAll();
        })
    }
    
    onLigneSelectedEvent(reception: ReceptionLigneBean) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('commandeCode', this.bean.commandeCode.value);
        queryParams = queryParams.append('ligneId', reception.ligne.ligneId.value);
        this.http.get(`achat-vente/commande/reception/ligne`, { params: queryParams })
        .pipe(map((e: any) => <any>e))
        .subscribe(data => {
            reception.ligne = data.record;
            reception.unite.uniteCode.valueOptions = reception.ligne.unite.uniteCode.valueOptions
        })
    }

}
