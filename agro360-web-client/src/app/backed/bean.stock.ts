import { FieldMetadata } from './metadata';
import { StatutCaisseEnumVd, TypeArticleEnumVd, TypeOperationEnumVd } from './vd.stock';
import { AbstractBean, AbstractFormBean, AbstractLigneBean } from './bean.common';
import { TiersBean } from './bean.tiers';

export interface ArticleSearchBean extends AbstractBean {
	articleCode: FieldMetadata<string>;
	typeArticle: FieldMetadata<TypeArticleEnumVd>;
};

export interface OperationCaisseBean extends AbstractLigneBean {
	typeOperation: FieldMetadata<TypeOperationEnumVd>;
	dateOperation: FieldMetadata<any>;
};

export interface UniteBean extends AbstractFormBean {
	description: FieldMetadata<string>;
	abreviation: FieldMetadata<string>;
	uniteCode: FieldMetadata<string>;
};

export interface ArticleBean extends AbstractFormBean {
	unite: UniteBean;
	description: FieldMetadata<string>;
	articleCode: FieldMetadata<string>;
	typeArticle: FieldMetadata<TypeArticleEnumVd>;
	variants: Array<VariantBean>;
	conversions: Array<ConversionBean>;
};

export interface CasierBean extends AbstractFormBean {
	casierCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
};

export interface CaisseBean extends AbstractFormBean {
	statut: FieldMetadata<StatutCaisseEnumVd>;
	agent: TiersBean;
	note: FieldMetadata<string>;
	operationsCaisse: Array<OperationCaisseBean>;
	magasin: MagasinBean;
	journee: FieldMetadata<any>;
};

export interface ConversionBean extends AbstractFormBean {
	unite: UniteBean;
	facteur: FieldMetadata<number>;
};

export interface MagasinBean extends AbstractFormBean {
	description: FieldMetadata<string>;
	magasinCode: FieldMetadata<string>;
	casiers: Array<CasierBean>;
};

export interface VariantBean extends AbstractFormBean {
	description: FieldMetadata<string>;
	variantCode: FieldMetadata<string>;
};