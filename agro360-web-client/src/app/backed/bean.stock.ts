import { FieldMetadata } from './metadata';
import { ArticleTypeEnumVd, OperationTypeEnumVd } from './vd.stock';
import { AbstractBean, AbstractSearchBean } from './bean.common';
import { TaxeBean } from './bean.finance';

export interface InventaireSearchBean extends AbstractSearchBean {
	magasinCode: FieldMetadata<string>;
	articleCode: FieldMetadata<string>;
	createInventaireBtn: FieldMetadata<string>;
	uniteBtn: FieldMetadata<string>;
};

export interface ArticleSearchBean extends AbstractSearchBean {
	articleCode: FieldMetadata<string>;
	uniteBtn: FieldMetadata<string>;
	type: FieldMetadata<ArticleTypeEnumVd>;
};

export interface ConversionBean extends AbstractBean {
	unite: UniteBean;
	facteur: FieldMetadata<number>;
};

export interface UniteBean extends AbstractBean {
	description: FieldMetadata<string>;
	uniteCode: FieldMetadata<string>;
};

export interface OperationSearchBean extends AbstractSearchBean {
	variantCode: FieldMetadata<string>;
	magasinCode: FieldMetadata<string>;
	articleCode: FieldMetadata<string>;
};

export interface MagasinSearchBean extends AbstractSearchBean {
	magasinCode: FieldMetadata<string>;
	createMagasinBtn: FieldMetadata<string>;
	uniteBtn: FieldMetadata<string>;
};

export interface OperationBean extends AbstractBean {
	variantCode: FieldMetadata<string>;
	inventaireAvant: FieldMetadata<number>;
	magasinCode: FieldMetadata<string>;
	quantite: FieldMetadata<number>;
	prixUnitaire: FieldMetadata<number>;
	articleCode: FieldMetadata<string>;
	date: FieldMetadata<any>;
	operationId: FieldMetadata<number>;
	type: FieldMetadata<OperationTypeEnumVd>;
};

export interface VariantBean extends AbstractBean {
	variantCode: FieldMetadata<string>;
	alias: FieldMetadata<string>;
	articleCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
};

export interface ArticleBean extends AbstractBean {
	unite: UniteBean;
	conversions: Array<ConversionBean>;
	taxes: Array<ArticleTaxeBean>;
	variants: Array<VariantBean>;
	articleCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
	type: FieldMetadata<ArticleTypeEnumVd>;
};

export interface InventaireBean extends AbstractBean {
	variantCode: FieldMetadata<string>;
	uniteAchat: UniteBean;
	magasin: MagasinBean;
	quantite: FieldMetadata<number>;
	article: ArticleBean;
	alias: FieldMetadata<string>;
	prixUnitaireAchat: FieldMetadata<number>;
	prixUnitaireVente: FieldMetadata<number>;
	prixUnitaireVenteAjust: FieldMetadata<number>;
	uniteVente: UniteBean;
	variantDescription: FieldMetadata<string>;
	defPrixVenteBtn: FieldMetadata<string>;
	quantiteAjust: FieldMetadata<number>;
	ajustQteBtn: FieldMetadata<string>;
};

export interface ArticleTaxeBean extends AbstractBean {
	selected: FieldMetadata<boolean>;
	taxe: TaxeBean;
};

export interface MagasinBean extends AbstractBean {
	magasinCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
};

export interface UniteSearchBean extends AbstractSearchBean {
	uniteCode: FieldMetadata<string>;
};