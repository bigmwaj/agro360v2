import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';
import { MatInputModule } from '@angular/material/input';
import { map } from 'rxjs';
import { CommandeBean, RetourLigneBean } from 'src/app/backed/bean.av';
import { Message } from 'src/app/backed/message';
import { BeanList } from 'src/app/modules/common/bean/bean.list';
import { BeanTools } from 'src/app/modules/common/bean/bean.tools';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';

@Component({
    standalone: true,
    imports: [
        SharedModule,
        MatInputModule,        
        FormsModule
    ],
    selector: 'achat-vente-retour-list',
    templateUrl: './retour.list.component.html'
})
export class RetourListComponent extends BeanList<RetourLigneBean> {
    
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
    
    override getKeyLabel(bean: RetourLigneBean): string | number {
        return bean.retourId.value;
    }

    ngOnInit(): void {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('commandeCode', this.bean.commandeCode.value);

        this.http
            .get(`achat-vente/commande/retour`, { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.setData(data.records);
            });
    }
  
    override setData(data: RetourLigneBean[]){
        super.setData(data);
    }

    addAction() {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('commandeCode', this.bean.commandeCode.value);
        
        this.http
            .get(`achat-vente/commande/retour/create-form`, { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.prependItem(data.record);
            });
    }

    saveAction() {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('commandeCode', this.bean.commandeCode.value);

        this.http.post(`achat-vente/commande/retour`, BeanTools.reviewBeansAction(this.getData()), { params: queryParams })
        .pipe(map((e: any) => <any>e))
        .subscribe(data => {
            this.dialog.closeAll();
        })
    }
    
    onLigneSelectedEvent(retour: RetourLigneBean) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('commandeCode', this.bean.commandeCode.value);
        queryParams = queryParams.append('ligneId', retour.ligne.ligneId.value);
        this.http.get(`achat-vente/commande/retour/ligne`, { params: queryParams })
        .pipe(map((e: any) => <any>e))
        .subscribe(data => {
            retour.ligne = data.record;
            retour.unite.uniteCode.valueOptions = retour.ligne.unite.uniteCode.valueOptions
        })
    }

}
