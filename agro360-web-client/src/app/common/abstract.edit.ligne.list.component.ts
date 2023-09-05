import { HttpClient, HttpParams } from '@angular/common/http';
import { Component, Input, OnInit, ViewChild, EventEmitter, Output } from '@angular/core';
import { MatTable } from '@angular/material/table';
import { map } from 'rxjs';
import { ArticleBean, ArticleSearchBean, ConversionBean, UniteBean, VariantBean } from 'src/app/backed/bean.stock';
import { FieldMetadata } from 'src/app/backed/metadata';
import { EditActionEnumVd } from 'src/app/backed/vd.common';
import { TypeArticleEnumVd, TypeLigneEnumVd } from 'src/app/backed/vd.stock';
import { BeanList } from 'src/app/common/bean.list';
import { CommonUtlis } from 'src/app/common/utils/common.utils';
import { StockUtils } from '../modules/stock/stock.utils';
import { AbstractLigneBean } from '../backed/bean.common';

@Component({
    selector: 'common-edit-ligne-list',
    template: ``
})
export abstract class AbstractEditLigneListComponent<B extends AbstractLigneBean> extends BeanList<B> implements OnInit {

    getKeyLabel(bean: B): string | number {
        return bean.ligneId.value;
    }

    @Input()
    public ownerId: {};

    @Input()
    public lignes: Array<B>;
    
    @Output()
    updateOwnerPrixTotal = new EventEmitter();

    @ViewChild(MatTable)
    private table: MatTable<B>;

    override getViewChild(): MatTable<B> {
        return this.table;
    }
    
    selectUniteFromList(bean: B): boolean{
        return [ TypeLigneEnumVd.ARTC, TypeLigneEnumVd.SSTD].includes(bean.typeLigne.value);
    }

    selectArticleFromList(bean: B): boolean{
        return [ TypeLigneEnumVd.ARTC, TypeLigneEnumVd.SSTD].includes(bean.typeLigne.value);
    }

    protected abstract getCreateFormUrl():string

    constructor(public http: HttpClient) {
        super()
    }

    ngOnInit(): void {
        this.setData(this.lignes)
    }

    override setData(data: B[]){
        super.setData(data);
        data.forEach(e => {
            this.onSelectTypeLigneEvent(e);
            this.onSelectArticleEvent(e);
            this._updatePrixTotal(e);
        })
    }
    
    override addItem(bean: B){
        super.addItem(bean);
        this.onSelectTypeLigneEvent(bean);
        this.onSelectArticleEvent(bean);
        this._updatePrixTotal(bean);
    }
    
    override removeItem(bean: B){
        super.removeItem(bean);
        this.updateOwnerPrixTotal.emit();
    }
    
    onSelectTypeLigneEvent(bean: B) {
        if (bean.typeLigne.value) {
            switch (bean.typeLigne.value) {
                case TypeLigneEnumVd.ARTC:
                    this.initSelectArticleOptions(bean)
                    break;

                case TypeLigneEnumVd.SSTD:
                    this.initSelectServiceOptions(bean);
                    break;

                default:                    
                    break;
            }
        }
    }
    
    onSelectArticleEvent(bean: B) {
        bean.unite.uniteCode.valueOptions = {}
        bean.variantCode.valueOptions = {}
        const types = [TypeLigneEnumVd.ARTC, TypeLigneEnumVd.SSTD];
        if( !bean.article.articleCode.value || !types.includes(bean.typeLigne.value) ){
            return;
        }

        let queryParams = new HttpParams();
        queryParams = queryParams.append("articleCode", bean.article.articleCode.value);
        this.http
            .get<any>(`${CommonUtlis.BASE_URL}/stock/article/update-form`, { params: queryParams })
            .pipe(map((data: any) =><ArticleBean> data))
            .subscribe(article => { 
                this.initSelectUniteOptions(bean, article.unite, article.conversions);                
                this.initSelectVariantOptions(bean, article.variants);
            });
    }
    
    
    updatePrixTotalEvent(bean: B) {
        this._updatePrixTotal(bean)
        this.updateOwnerPrixTotal.emit();
    }

    addAction() {
        let queryParams = new HttpParams();
        queryParams = queryParams.appendAll(this.ownerId);
        this.__add(queryParams);
    }

    copyAction(ligne: B) {
        let queryParams = new HttpParams();
        queryParams = queryParams.appendAll(this.ownerId);
        queryParams = queryParams.append('copyFrom', ligne.ligneId.value);

        this.__add(queryParams);
    }

    deleteAction(ligne: B) {
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

    private getNextNumero():number{
        const curr = this.getData().map(e => e.numero.value).reduce((acc, current) => Math.max(acc, current), 0);
        return Math.floor(curr / 10) * 10 + 10;
    }

    protected __add(queryParams: HttpParams) {
        this.http
            .get(`${CommonUtlis.BASE_URL}${this.getCreateFormUrl()}`, { params: queryParams })
            .pipe(map((data: any) => <B>data))
            .subscribe(ligne => {
                ligne.numero.value = this.getNextNumero()
                this.addItem(ligne);
                this.updateOwnerPrixTotal.emit();
            });
    }

    private initSelectArticleOrServiceOptions(bean: B, type: TypeArticleEnumVd.ARTC | TypeArticleEnumVd.SSTD){
        const searchQuery = <ArticleSearchBean>{};
        searchQuery.typeArticle = <FieldMetadata<TypeArticleEnumVd>>{};
        searchQuery.typeArticle.value = type;

        StockUtils.getArticlesAsValueOptions(this.http, searchQuery)
            .subscribe( (data:any) => bean.article.articleCode.valueOptions = data)
    }

    private initSelectArticleOptions(bean: B){
        this.initSelectArticleOrServiceOptions(bean, TypeArticleEnumVd.ARTC);
    }

    private initSelectServiceOptions(bean: B){
        this.initSelectArticleOrServiceOptions(bean, TypeArticleEnumVd.SSTD);
    }

    private _updatePrixTotal(bean: B) {
        bean.prixTotal.value = 0;
        if( bean.prixUnitaire.value && bean.quantite.value ){
            bean.prixTotal.value = bean.prixUnitaire.value * bean.quantite.value;
        }
    }

    private initSelectUniteOptions(bean:B, uniteBase:UniteBean, conversions: Array<ConversionBean>){
        const options = conversions.map((e: ConversionBean) => e.unite)
            .map(StockUtils.uniteKeyValMapper)
            .concat(StockUtils.uniteKeyValMapper(uniteBase))
            .join(",")
        bean.unite.uniteCode.valueOptions = JSON.parse(`{${options}}`);
    }
    
    private initSelectVariantOptions(bean:B, variants: Array<VariantBean>){
        const options = variants
            .map((e: VariantBean) => `"${e.variantCode.value}":"${e.variantCode.value} ${e.description.value}"`)
            .join(",")
        bean.variantCode.valueOptions = JSON.parse(`{${options}}`);
    }
}
