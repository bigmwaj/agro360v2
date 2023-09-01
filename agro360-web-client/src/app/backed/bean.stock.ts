import { FieldMetadata } from './metadata';
import { StatusCaisseEnumVd, TypeArticleEnumVd, TypeOperationEnumVd } from './vd.stock';
import { AbstractBean, AbstractLigneBean, AbstractStatusTrackingFormBean } from './bean.common';
import { TiersBean } from './bean.tiers';

export interface MagasinSearchBean extends AbstractBean {
	description: FieldMetadata<string>;
	magasinCode: FieldMetadata<string>;
};

export interface ArticleBean extends AbstractBean {
	unite: UniteBean;
	description: FieldMetadata<string>;
	articleCode: FieldMetadata<string>;
	typeArticle: FieldMetadata<TypeArticleEnumVd>;
	variants: Array<VariantBean>;
	conversions: Array<ConversionBean>;
};

export interface CasierBean extends AbstractBean {
	casierCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
};

export interface CaisseBean extends AbstractStatusTrackingFormBean<StatusCaisseEnumVd> {
	status: FieldMetadata<StatusCaisseEnumVd>;
	agent: TiersBean;
	note: FieldMetadata<string>;
	plusVendus: FieldMetadata<string>;
	plusAchetes: FieldMetadata<string>;
	depense: FieldMetadata<number>;
	recette: FieldMetadata<number>;
	solde: FieldMetadata<number>;
	operations: Array<OperationCaisseBean>;
	magasin: MagasinBean;
	journee: FieldMetadata<any>;
};

export interface OperationCaisseBean extends AbstractLigneBean {
	typeOperation: FieldMetadata<TypeOperationEnumVd>;
	dateOperation: FieldMetadata<any>;
};

export interface UniteSearchBean extends AbstractBean {
	description: FieldMetadata<string>;
	uniteCode: FieldMetadata<string>;
};

export interface UniteBean extends AbstractBean {
	description: FieldMetadata<string>;
	uniteCode: FieldMetadata<string>;
};

export interface MagasinBean extends AbstractBean {
	description: FieldMetadata<string>;
	magasinCode: FieldMetadata<string>;
	casiers: Array<CasierBean>;
};

export interface ConversionBean extends AbstractBean {
	unite: UniteBean;
	facteur: FieldMetadata<number>;
};

export interface VariantBean extends AbstractBean {
	description: FieldMetadata<string>;
	variantCode: FieldMetadata<string>;
};

export interface ArticleSearchBean extends AbstractBean {
	articleCode: FieldMetadata<string>;
	typeArticle: FieldMetadata<TypeArticleEnumVd>;
};

export interface CaisseIdBean {
	magasin: string;
	agent: string;
	journee: any;
};

export interface CaisseSearchBean extends AbstractBean {
	journeeDebut: FieldMetadata<any>;
	journeeFin: FieldMetadata<any>;
	magasin: FieldMetadata<string>;
	agent: FieldMetadata<string>;
	status: FieldMetadata<StatusCaisseEnumVd>;
};