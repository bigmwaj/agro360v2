import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { map } from 'rxjs';
import { CommandeBean, LigneBean } from 'src/app/backed/bean.av';
import { ArticleBean, ArticleSearchBean, ConversionBean, UniteBean, VariantBean } from 'src/app/backed/bean.stock';
import { Message } from 'src/app/backed/message';
import { FieldMetadata } from 'src/app/backed/metadata';
import { LigneTypeEnumVd, RemiseTypeEnumVd } from 'src/app/backed/vd.av';
import { ClientOperationEnumVd } from 'src/app/backed/vd.common';
import { ArticleTypeEnumVd } from 'src/app/backed/vd.stock';
import { BeanList } from 'src/app/modules/common/bean.list';
import { UIService } from 'src/app/modules/common/service/ui.service';
import { SharedModule } from 'src/app/modules/common/shared.module';
import { StockUtils } from '../../stock/stock.utils';
import { DisplayLigneTaxeDialogComponent } from './display.ligne.taxe.dialog.component';
import { EditRemiseDialogComponent } from './edit.remise.dialog.component';
import { EditRetourDialogComponent } from './edit.retour.dialog.component';
import { EditReceptionDialogComponent } from './edit.reception.dialog.component';

@Component({
    standalone: true,
    imports: [
        SharedModule
    ],
    selector: 'achat-vente-edit-ligne-list',
    templateUrl: './edit.ligne.list.component.html'
})
export class EditLigneListComponent extends BeanList<LigneBean> {
    
    displayedColumns: string[] = [
        'select',
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

    constructor(
        public http: HttpClient,
        public dialog: MatDialog,
        private ui: UIService) {
        super()
    }

    @Input({required:true})
    public commande: CommandeBean;
    
    @Output()
    updatePrixCommande = new EventEmitter();

    public alias:FieldMetadata<string> = {
        label:'Variant ou alias',
        editable:true
    } as FieldMetadata<string>;
    
    override getKeyLabel(bean: LigneBean): string | number {
        return bean.ligneId.value;
    }

    ngOnInit(): void {
        this.setData(this.commande.lignes)
    }

    isStandard(bean: LigneBean): boolean{
        return [ LigneTypeEnumVd.ARTC, LigneTypeEnumVd.SSTD ].includes(bean.type.value);
    }
  
    override setData(data: LigneBean[]){
        super.setData(data);

        data.forEach(e => {
            this.onSelectLigneTypeEvent(e);
            this.onSelectArticleEvent(e);
        })
    }
   
    override prependItem(bean: LigneBean){
        super.prependItem(bean);
        this.onSelectLigneTypeEvent(bean);
        this.onSelectArticleEvent(bean);
        this.updatePrix(bean);
    }
    
    override removeItem(bean: LigneBean){
        super.removeItem(bean);
        this.updatePrixCommande.emit();
    }

    onSelectLigneTypeEvent(bean: LigneBean) {
        if (bean.type.value) {
            switch (bean.type.value) {
                case LigneTypeEnumVd.ARTC:
                    this.initSelectArticleOptions(bean)
                    break;

                case LigneTypeEnumVd.SSTD:
                    this.initSelectServiceOptions(bean);
                    break;

                default:                    
                    break;
            }
        }
    }
    
    onSelectArticleEvent(bean: LigneBean) {
        bean.unite.uniteCode.valueOptions = {}
        bean.variantCode.valueOptions = {}
        const types = [LigneTypeEnumVd.ARTC, LigneTypeEnumVd.SSTD];
        if( !bean.article.articleCode.value || !types.includes(bean.type.value) ){
            return;
        }

        let queryParams = new HttpParams();
        queryParams = queryParams.append("articleCode", bean.article.articleCode.value);
        this.http
            .get<any>(`stock/article/edit-form`, { params: queryParams })
            .pipe(map((data: any) =><ArticleBean> data))
            .subscribe(article => { 
                this.initSelectUniteOptions(bean, article.unite, article.conversions);                
                this.initSelectVariantOptions(bean, article.variants);
            });
    }
    
    updatePrixEvent(bean: LigneBean) {
        this.updatePrix(bean);
        this.updatePrixCommande.emit();
    }

    addAction(alias?:string) {
        let queryParams = new HttpParams();
        queryParams = queryParams.append('commandeCode', this.commande.commandeCode.value);
        if( alias ){
            queryParams = queryParams.append('alias', this.alias.value);
            queryParams = queryParams.append('magasinCode', this.commande.magasin.magasinCode.value);
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
                this.alias.value = ''; // Pour la création à partir de l'alias
            });
    }

    addFromAliasAction() {
        this.addAction(this.alias.value);
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
        this.dialog.open(DisplayLigneTaxeDialogComponent, { 
            data: {
                ligne:bean,
                commande:this.commande
            } 
        });
    }

    editRemiseAction(bean: LigneBean) {
        let dialogRef = this.dialog.open(EditRemiseDialogComponent, { data: bean });
        dialogRef.afterClosed().subscribe(result => {
            this.updatePrix(bean)
        });  
    }
        
    editRetourAction(bean: LigneBean) {
        let dialogRef = this.dialog.open(EditRetourDialogComponent, { data: {ligne:bean, commande: this.commande } });
        dialogRef.afterClosed().subscribe(result => {
            console.log(`TODO: Ne pas raffraichir si l'utilisateur n'a pas soumis le formulaire`)
            console.log(result)
        });  
    }
        
    editReceptionAction(bean: LigneBean) {
        let dialogRef = this.dialog.open(EditReceptionDialogComponent, { data: {ligne:bean, commande: this.commande }});
        dialogRef.afterClosed().subscribe(result => {
            console.log(`TODO: Ne pas raffraichir si l'utilisateur n'a pas soumis le formulaire`)
            console.log(result)
        });  
    }

    private initSelectArticleOrServiceOptions(bean: LigneBean, type: ArticleTypeEnumVd.ARTC | ArticleTypeEnumVd.SSTD){
        const searchQuery = <ArticleSearchBean>{};
        searchQuery.type = <FieldMetadata<ArticleTypeEnumVd>>{};
        searchQuery.type.value = type;

        StockUtils.getArticlesAsValueOptions(this.http, searchQuery)
            .subscribe( (data:any) => bean.article.articleCode.valueOptions = data)
    }

    private initSelectArticleOptions(bean: LigneBean){
        this.initSelectArticleOrServiceOptions(bean, ArticleTypeEnumVd.ARTC);
    }

    private initSelectServiceOptions(bean: LigneBean){
        this.initSelectArticleOrServiceOptions(bean, ArticleTypeEnumVd.SSTD);
    }

    private updatePrix(bean: LigneBean) {
        this.http
            .post(`achat-vente/commande/ligne/refresh-prix`, bean)  
            .pipe(map((data: any) => data))
            .subscribe((data: LigneBean) => {
                bean.prixTotalHT.value = data.prixTotalHT.value;
                bean.prixTotal.value = data.prixTotal.value;
                bean.taxe.value = data.taxe.value; 
                bean.remise.value = data.remise.value;

                this.updatePrixCommande.emit();

            });
        
    }

    private initSelectUniteOptions(bean:LigneBean, uniteBase:UniteBean, conversions: Array<ConversionBean>){
        const options = conversions.map((e: ConversionBean) => e.unite)
            .map(StockUtils.uniteKeyValMapper)
            .concat(StockUtils.uniteKeyValMapper(uniteBase))
            .join(",")
        bean.unite.uniteCode.valueOptions = JSON.parse(`{${options}}`);
    }
    
    private initSelectVariantOptions(bean:LigneBean, variants: Array<VariantBean>){
        const options = variants
            .map((e: VariantBean) => `"${e.variantCode.value}":"${e.variantCode.value} ${e.description.value}"`)
            .join(",")
        bean.variantCode.valueOptions = JSON.parse(`{${options}}`);
    }
}
