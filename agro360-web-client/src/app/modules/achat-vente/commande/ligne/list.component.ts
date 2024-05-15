import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatAutocompleteSelectedEvent } from '@angular/material/autocomplete';
import { MatDialog } from '@angular/material/dialog';
import { MatDividerModule } from '@angular/material/divider';
import { Observable, map } from 'rxjs';
import { CommandeBean, LigneBean } from 'src/app/backed/bean.av';
import { ArticleBean, VariantBean } from 'src/app/backed/bean.stock';
import { Message } from 'src/app/backed/message';
import { FieldMetadata } from 'src/app/backed/metadata';
import { LigneTypeEnumVd } from 'src/app/backed/vd.av';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';
import { BeanList } from 'src/app/modules/common/bean/bean.list';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { RemiseDialogComponent } from './remise.dialog.component';
import { TaxeListDialogComponent } from './taxe.list.dialog.component';

@Component({
    standalone: true,
    imports: [
        SharedModule,
        MatDividerModule
    ],
    selector: 'achat-vente-ligne-list',
    templateUrl: './list.component.html'
})
export class ListComponent extends BeanList<LigneBean> {
    
    displayedColumns: string[] = [
        'quantite',
        'prixUnitaire',
        'prixTotal',
        'type',
        'article',
        'variantCode',
        'unite',
        'remise',
        'taxe',
        'actions'
    ];

    @Input({required:true})
    public commande: CommandeBean;
    
    @Output()
    updatePrixCommande = new EventEmitter();

    public alias:FieldMetadata<string> = {
        label:'Alias', 
        tooltip: `Si vous connaissez l'alias de la variante de l'article que vous souhaitez ajouter, merci de le saisir ici et valider.`,
        editable:true,
        value:''
    } as FieldMetadata<string>;

    public query:FieldMetadata<string> = {
        label:'Rechercher',
        editable:true,
        value:''
    } as FieldMetadata<string>;

    articleLookupUrl = `achat-vente/commande/ligne/article-option`;

    constructor(
        public http: HttpClient,
        public dialog: MatDialog,
        private ui: UIService) {
        super()
    }

    ngOnInit(): void {
        this.setData(this.commande.lignes);
        this.query.editable = this.commande.createLigneBtn.visible;
        this.alias.editable = this.commande.createLigneBtn.visible;
    }

    mapVariantToLabelFn(variant: VariantBean): string {
        return variant.variantCode.value + ' - ' + variant.description.value;
    }

    mapVariantToKeyFn(variant: VariantBean): string {
        return variant.alias.value;
    }

    mapArticleToLabelFn(article: ArticleBean): string {
        return article.articleCode.value + ' - ' + article.description.value;
    }

    mapArticleToKeyFn(article: ArticleBean): string {
        return article.articleCode.value;
    }

    variantLookupFn(query:string):Observable<any>{
        let queryParams = new HttpParams();
        queryParams = queryParams.append("query", query);
        return this.http.get(`achat-vente/commande/ligne/variant-query`, { params: queryParams });
    }
    
    articleLookupFn(query:string, bean:LigneBean):Observable<any>{
        let queryParams = new HttpParams();
        queryParams = queryParams.append('type', bean.type.value);
        queryParams = queryParams.append("query", query);
        return this.http.get(`achat-vente/commande/ligne/article-query`, { params: queryParams });
    }

    isStandard(bean: LigneBean): boolean{
        return [ LigneTypeEnumVd.ARTC, LigneTypeEnumVd.SSTD ].includes(bean.type.value);
    }

    override getKeyLabel(bean: LigneBean): string | number {
        return bean.ligneId.value;
    }
   
    override prependItem(bean: LigneBean){
        super.prependItem(bean);
        this.updatePrixCalcule(bean);
    }
    
    override removeItem(bean: LigneBean){
        super.removeItem(bean);
        this.updatePrixCommande.emit();
    }

    refreshPrixUnitaireEvent(bean: LigneBean) {
        const uniteCode = bean.unite.uniteCode.value;
        const variantCode = bean.variantCode.value;
        const magasinCode = this.commande.magasin.magasinCode.value;
        const articleCode = bean.article.articleCode.value;

        if( uniteCode && variantCode ){
            const type = this.commande.type.value;
            
            let queryParams = new HttpParams();
            queryParams = queryParams.append('type', type);
            queryParams = queryParams.append('magasinCode', magasinCode);
            queryParams = queryParams.append('articleCode', articleCode);
            queryParams = queryParams.append('variantCode', variantCode);
            queryParams = queryParams.append('uniteCode', uniteCode);

            this.http
                .get(`achat-vente/commande/ligne/prix-unitaire`, { params: queryParams })
                .pipe(map((data: any) => data))
                .subscribe(data => {
                    bean.prixUnitaire.value = data;
                    this.updatePrixCalcule(bean);
                });
        }
    }

    onSelectLigneTypeEvent(bean: LigneBean) {
        bean.unite.uniteCode.valueOptions = {}
        bean.variantCode.valueOptions = {}
        bean.article.articleCode.valueOptions = {}
        bean.article.articleCode.value = ''
    }
    
    onSelectArticleEvent(bean: LigneBean) {
        bean.unite.uniteCode.valueOptions = {}
        bean.variantCode.valueOptions = {}

        const types = [LigneTypeEnumVd.ARTC, LigneTypeEnumVd.SSTD];
        const articleCode = bean.article.articleCode.value;

        if( !articleCode || !types.includes(bean.type.value) ){
            return;
        }

        let queryParams = new HttpParams();
        queryParams = queryParams.append("articleCode", articleCode);
        this.http
            .get(`achat-vente/commande/ligne/variant-option`, { params: queryParams })
            .subscribe(data => { 
                bean.variantCode.valueOptions = data;
                const keys = Object.keys(data)
                if( keys.length == 1){
                    bean.variantCode.value = keys[0]
                }
            });

        this.http
            .get(`achat-vente/commande/ligne/unite-option`, { params: queryParams }) 
            .subscribe(data => { 
                bean.unite.uniteCode.valueOptions = data;
                const keys = Object.keys(data)
                if( keys.length == 1){
                    bean.unite.uniteCode.value = keys[0]
                }
            });
    }
    
    updatePrixEvent(bean: LigneBean) {
        this.updatePrixCalcule(bean);
        this.updatePrixCommande.emit();
    }

    addAction(alias?:string) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('commandeCode', this.commande.commandeCode.value);
        queryParams = queryParams.append('type', this.commande.type.value);
        queryParams = queryParams.append('magasinCode', this.commande.magasin.magasinCode.value);

        if( alias ){
            queryParams = queryParams.append('alias', alias);
        }
        this.http
            .get(`achat-vente/commande/ligne/create-form`, { params: queryParams })
            .pipe(map((data: any) => data))
            .subscribe(data => {
                this.prependItem(data.record);

                if( (<Message[]>data.messages).length > 0 ){
                    this.ui.displayFlashMessage(<Message[]>data.messages);
                }
                this.updatePrixCommande.emit();
                this.alias.value = '';
                this.query.value = '';
            });
    }

    addFromAliasAction() {
        this.addAction(this.alias.value);
    }

    addFromQueryAction($event: MatAutocompleteSelectedEvent) {
        const value = $event.option.value;
        this.addAction(value);
    }

    copyAction(ligne: LigneBean) {
        const copy:LigneBean = JSON.parse(JSON.stringify(ligne)); //deep clone
        copy.action = ClientOperationEnumVd.CREATE
        copy.ligneId.value = 0
        this.prependItem(copy);
        this.updatePrixCommande.emit();
    }

    deleteAction(ligne: LigneBean) {
        if( ligne.action == ClientOperationEnumVd.CREATE ){
            this.removeItem(ligne);
        }else {
            if( ligne.action != ClientOperationEnumVd.DELETE){
                ligne.action = ClientOperationEnumVd.DELETE;
                ligne.valueChanged = true;
            }else{                
                ligne.action = ClientOperationEnumVd.UPDATE;
                ligne.valueChanged = true;
            }
            this.updatePrixCommande.emit();
        }
    }
    
    listerTaxesAction(bean: LigneBean) {
        this.dialog.open(TaxeListDialogComponent, { 
            data: {
                ligne:bean,
                commande:this.commande
            } 
        });
    }

    editRemiseAction(bean: LigneBean) {
        let dialogRef = this.dialog.open(RemiseDialogComponent, { data: bean });
        dialogRef.afterClosed().subscribe(result => {
            if( this.commande.createLigneBtn.visible ){
                this.updatePrixCalcule(bean)
            }
        });  
    }

    private updatePrixCalcule(bean: LigneBean) {        
        let queryParams = new HttpParams();
        queryParams = queryParams.append('type', this.commande.type.value);

        this.http
            .post(`achat-vente/commande/ligne/refresh-prix`, bean, { params: queryParams })  
            .pipe(map((data: any) => data))
            .subscribe((data: LigneBean) => {
                bean.prixTotalHT.value = data.prixTotalHT.value;
                bean.prixTotal.value = data.prixTotal.value;
                bean.taxe.value = data.taxe.value; 
                bean.remise.value = data.remise.value;

                this.updatePrixCommande.emit();

            });
        
    }
}
