import { FieldMetadata } from './metadata';
import { ArticleTypeEnumVd, OperationTypeEnumVd } from './vd.stock';
import { AbstractBean } from './bean.common';
import { TaxeBean } from './bean.finance';

export interface ArticleSearchBean extends AbstractBean {
	articleCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
	type: FieldMetadata<ArticleTypeEnumVd>;
};

export interface UniteSearchBean extends AbstractBean {
	description: FieldMetadata<string>;
	uniteCode: FieldMetadata<string>;
};

export interface InventaireSearchBean extends AbstractBean {
	magasinCode: FieldMetadata<string>;
	articleCode: FieldMetadata<string>;
};

export interface UniteBean extends AbstractBean {
	description: FieldMetadata<string>;
	uniteCode: FieldMetadata<string>;
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

export interface OperationSearchBean extends AbstractBean {
	variantCode: FieldMetadata<string>;
	magasinCode: FieldMetadata<string>;
	articleCode: FieldMetadata<string>;
};

export interface MagasinBean extends AbstractBean {
	magasinCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
	casiers: Array<CasierBean>;
};

export interface MagasinSearchBean extends AbstractBean {
	magasinCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
};

export interface InventaireBean extends AbstractBean {
	variantCode: FieldMetadata<string>;
	magasin: MagasinBean;
	quantite: FieldMetadata<number>;
	article: ArticleBean;
	prixUnitaireAchat: FieldMetadata<number>;
	prixUnitaireVente: FieldMetadata<number>;
};

export interface ConversionBean extends AbstractBean {
	unite: UniteBean;
	facteur: FieldMetadata<number>;
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

export interface VariantBean extends AbstractBean {
	variantCode: FieldMetadata<string>;
	alias: FieldMetadata<string>;
	articleCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
};

export interface ArticleTaxeBean extends AbstractBean {
	selected: FieldMetadata<boolean>;
	taxe: TaxeBean;
};

export interface CasierBean extends AbstractBean {
	casierCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
};