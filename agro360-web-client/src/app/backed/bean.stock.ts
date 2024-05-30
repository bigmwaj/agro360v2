import { FieldMetadata } from './metadata';
import { ArticleTypeEnumVd, OperationTypeEnumVd } from './vd.stock';
import { AbstractBean, AbstractSearchBean } from './bean.common';
import { TaxeBean } from './bean.finance';

export interface ImportInventaireBean extends AbstractBean {
	uniteAchat: UniteBean;
	magasin: MagasinBean;
	prixUnitaireVenteAjust: FieldMetadata<any>;
	article: ArticleBean;
	defPrixVenteBtn: FieldMetadata<any>;
	ajustQteBtn: FieldMetadata<any>;
	alias: FieldMetadata<any>;
	quantite: FieldMetadata<any>;
	quantiteAjust: FieldMetadata<any>;
	uniteStock: UniteBean;
	uniteVente: UniteBean;
	variantCode: FieldMetadata<any>;
	prixUnitaireVente: FieldMetadata<any>;
	prixUnitaireAchat: FieldMetadata<any>;
	variantDescription: FieldMetadata<any>;
};

export interface MagasinBean extends AbstractBean {
	description: FieldMetadata<any>;
	magasinCode: FieldMetadata<any>;
};

export interface ConversionBean extends AbstractBean {
	unite: UniteBean;
	facteur: FieldMetadata<any>;
};

export interface VariantBean extends AbstractBean {
	variantCode: FieldMetadata<any>;
	articleCode: FieldMetadata<any>;
	alias: FieldMetadata<any>;
	description: FieldMetadata<any>;
};

export interface ArticleSearchBean extends AbstractSearchBean {
	uniteBtn: FieldMetadata<any>;
	articleCode: FieldMetadata<any>;
	type: FieldMetadata<ArticleTypeEnumVd>;
};

export interface InventaireSearchBean extends AbstractSearchBean {
	uniteBtn: FieldMetadata<any>;
	articleCode: FieldMetadata<any>;
	createInventaireBtn: FieldMetadata<any>;
	magasinCode: FieldMetadata<any>;
};

export interface MagasinSearchBean extends AbstractSearchBean {
	createMagasinBtn: FieldMetadata<any>;
	uniteBtn: FieldMetadata<any>;
	magasinCode: FieldMetadata<any>;
};

export interface ArticleTaxeBean extends AbstractBean {
	taxe: TaxeBean;
	selected: FieldMetadata<any>;
};

export interface InventaireBean extends AbstractBean {
	uniteAchat: UniteBean;
	magasin: MagasinBean;
	prixUnitaireVenteAjust: FieldMetadata<any>;
	article: ArticleBean;
	defPrixVenteBtn: FieldMetadata<any>;
	ajustQteBtn: FieldMetadata<any>;
	alias: FieldMetadata<any>;
	quantite: FieldMetadata<any>;
	quantiteAjust: FieldMetadata<any>;
	uniteStock: UniteBean;
	uniteVente: UniteBean;
	variantCode: FieldMetadata<any>;
	prixUnitaireVente: FieldMetadata<any>;
	prixUnitaireAchat: FieldMetadata<any>;
	variantDescription: FieldMetadata<any>;
};

export interface UniteBean extends AbstractBean {
	uniteCode: FieldMetadata<any>;
	description: FieldMetadata<any>;
};

export interface UniteSearchBean extends AbstractSearchBean {
	uniteCode: FieldMetadata<any>;
};

export interface OperationBean extends AbstractBean {
	inventaireAvant: FieldMetadata<any>;
	variantCode: FieldMetadata<any>;
	operationId: FieldMetadata<any>;
	articleCode: FieldMetadata<any>;
	quantite: FieldMetadata<any>;
	date: FieldMetadata<any>;
	prixUnitaire: FieldMetadata<any>;
	type: FieldMetadata<OperationTypeEnumVd>;
	magasinCode: FieldMetadata<any>;
};

export interface ArticleBean extends AbstractBean {
	unite: UniteBean;
	conversions: Array<ConversionBean>;
	taxes: Array<ArticleTaxeBean>;
	variants: Array<VariantBean>;
	articleCode: FieldMetadata<any>;
	description: FieldMetadata<any>;
	type: FieldMetadata<ArticleTypeEnumVd>;
};

export interface OperationSearchBean extends AbstractSearchBean {
	variantCode: FieldMetadata<any>;
	articleCode: FieldMetadata<any>;
	magasinCode: FieldMetadata<any>;
};