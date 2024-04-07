import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { LigneBean } from 'src/app/backed/bean.av';
import { FieldMetadata } from 'src/app/backed/metadata';
import { ArticleTypeEnumVd } from 'src/app/backed/vd.stock';
import { SharedModule } from 'src/app/common/shared.module';
import { BeanList } from 'src/app/common/component/bean.list';
import { MatTable } from '@angular/material/table';
import { LigneTypeEnumVd } from 'src/app/backed/vd.av';
import { ArticleBean, ArticleSearchBean, ConversionBean, UniteBean, VariantBean } from 'src/app/backed/bean.stock';
import { map } from 'rxjs';
import { EditActionEnumVd } from 'src/app/backed/vd.common';
import { StockUtils } from '../../stock/stock.utils';

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
        'type',
        'article',
        'variantCode',
        'unite',
        'quantite',
        'prixUnitaire',
        'prixTotal',
        'actions'
    ];

    constructor(public http: HttpClient) {
        super()
    }

    @Input()
    public ownerId: {};

    @Input()
    public lignes: Array<LigneBean>;
    
    @Output()
    updateOwnerPrixTotal = new EventEmitter();

    @ViewChild(MatTable)
    private table: MatTable<LigneBean>;

    override getViewChild(): MatTable<LigneBean> {
        return this.table;
    }
    
    override getKeyLabel(bean: LigneBean): string | number {
        return bean.ligneId.value;
    }

    ngOnInit(): void {
        this.setData(this.lignes)
    }
    
    /**
     * Lorsque le type de la ligne est ARTC ou SSTD, alors, on change le type de champ unité en select.
     * Sinon ça reste un champ de texte afin que l'utilisateur puisse saisir le descriptif de l'article.
     * @param bean 
     * @returns <strong>true</strong> si le type de ligne est dans la liste (ARTC, SSTD)
     */
    selectUniteFromList(bean: LigneBean): boolean{
        return [ LigneTypeEnumVd.ARTC, LigneTypeEnumVd.SSTD ].includes(bean.type.value);
    }

    /**
     * Lorsque le type de la ligne est ARTC ou SSTD, alors, on change le type de champ article en select.
     * Sinon ça reste un champ de texte afin que l'utilisateur puisse saisir le descriptif de l'article.
     * @param bean 
     * @returns  <strong>true</strong> si le type de ligne est dans la liste (ARTC, SSTD)
     */
    selectArticleFromList(bean: LigneBean): boolean{
        return [ LigneTypeEnumVd.ARTC, LigneTypeEnumVd.SSTD ].includes(bean.type.value);
    }

    getCreateFormUrl():string{
        return 'achat-vente/commande/ligne/create-form';
    }

    override setData(data: LigneBean[]){
        super.setData(data);
        data.forEach(e => {
            this.onSelectLigneTypeEvent(e);
            this.onSelectArticleEvent(e);
            this.updatePrixTotal(e);
        })
    }
    
    override addItem(bean: LigneBean){
        super.addItem(bean);
        this.onSelectLigneTypeEvent(bean);
        this.onSelectArticleEvent(bean);
        this.updatePrixTotal(bean);
    }
    
    override removeItem(bean: LigneBean){
        super.removeItem(bean);
        this.updateOwnerPrixTotal.emit();
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
    
    updatePrixTotalEvent(bean: LigneBean) {
        this.updatePrixTotal(bean)
        this.updateOwnerPrixTotal.emit();
    }

    addAction() {
        let queryParams = new HttpParams();
        queryParams = queryParams.appendAll(this.ownerId);
        this.http
            .get(`${this.getCreateFormUrl()}`, { params: queryParams })
            .pipe(map((data: any) => <LigneBean>data))
            .subscribe(ligne => {
                this.addItem(ligne);
                this.updateOwnerPrixTotal.emit();
            });
    }

    copyAction(ligne: LigneBean) {
        const copy:LigneBean = JSON.parse(JSON.stringify(ligne)); //deep clone
        copy.action = EditActionEnumVd.CREATE
        copy.ligneId.value = 0
        this.addItem(copy);
        this.updateOwnerPrixTotal.emit();
    }

    deleteAction(ligne: LigneBean) {
        if( ligne.action == EditActionEnumVd.CREATE ){
            this.removeItem(ligne);
        }else {
            if( ligne.action != EditActionEnumVd.DELETE){
                ligne.action = EditActionEnumVd.DELETE;
                ligne.valueChanged = true;
            }else{                
                ligne.action = EditActionEnumVd.SYNC;
                ligne.valueChanged = false;
            }
            this.updatePrixTotalEvent(ligne)
        }
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

    private updatePrixTotal(bean: LigneBean) {
        bean.prixTotal.value = 0;
        if( bean.prixUnitaire.value && bean.quantite.value ){
            bean.prixTotal.value = bean.prixUnitaire.value * bean.quantite.value;
        }
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
