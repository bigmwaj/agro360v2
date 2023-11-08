import { FieldMetadata } from './metadata';
import { StatusCaisseEnumVd, TypeArticleEnumVd, TypeOperationEnumVd } from './vd.stock';
import { AbstractBean, AbstractLigneBean, AbstractStatusTrackingBean } from './bean.common';
import { TiersBean } from './bean.tiers';

export interface OperationCaisseBean extends AbstractLigneBean {
	typeOperation: FieldMetadata<TypeOperationEnumVd>;
	dateOperation: FieldMetadata<any>;
	heureOperation: FieldMetadata<any>;
	supprimer: FieldMetadata<string>;
};

export interface CaisseIdBean {
	journee: any;
	agent: string;
	magasin: string;
};

export interface VariantBean extends AbstractBean {
	description: FieldMetadata<string>;
	variantCode: FieldMetadata<string>;
};

export interface UniteBean extends AbstractBean {
	description: FieldMetadata<string>;
	uniteCode: FieldMetadata<string>;
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

export interface CaisseSearchBean extends AbstractBean {
	journeeFin: FieldMetadata<any>;
	journeeDebut: FieldMetadata<any>;
	magasin: FieldMetadata<string>;
	agent: FieldMetadata<string>;
	status: FieldMetadata<StatusCaisseEnumVd>;
};

export interface MagasinSearchBean extends AbstractBean {
	description: FieldMetadata<string>;
	magasinCode: FieldMetadata<string>;
};

export interface UniteSearchBean extends AbstractBean {
	description: FieldMetadata<string>;
	uniteCode: FieldMetadata<string>;
};

export interface ArticleSearchBean extends AbstractBean {
	articleCode: FieldMetadata<string>;
	typeArticle: FieldMetadata<TypeArticleEnumVd>;
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

export interface CaisseBean extends AbstractStatusTrackingBean<StatusCaisseEnumVd> {
	agent: TiersBean;
	recette: FieldMetadata<number>;
	plusVendus: FieldMetadata<string>;
	note: FieldMetadata<string>;
	depense: FieldMetadata<number>;
	operations: Array<OperationCaisseBean>;
	magasin: MagasinBean;
	journee: FieldMetadata<any>;
	solde: FieldMetadata<number>;
	plusAchetes: FieldMetadata<string>;
	status: FieldMetadata<StatusCaisseEnumVd>;
	ajouterOperation: FieldMetadata<string>;
	delete: FieldMetadata<string>;
	changeStatus: FieldMetadata<string>;
};