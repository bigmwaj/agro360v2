import { FieldMetadata } from './metadata';
import { ArticleTypeEnumVd, CaisseStatusEnumVd, OperationTypeEnumVd } from './vd.stock';
import { AbstractBean, AbstractLigneBean, AbstractStatusTrackingBean } from './bean.common';
import { PartnerBean } from './bean.core';

export interface CaisseBean extends AbstractStatusTrackingBean<CaisseStatusEnumVd> {
	magasin: MagasinBean;
	journee: FieldMetadata<any>;
	solde: FieldMetadata<number>;
	operations: Array<OperationCaisseBean>;
	depense: FieldMetadata<number>;
	plusVendus: FieldMetadata<string>;
	recette: FieldMetadata<number>;
	changeStatus: FieldMetadata<string>;
	Partner: PartnerBean;
	plusAchetes: FieldMetadata<string>;
	ajouterOperation: FieldMetadata<string>;
	note: FieldMetadata<string>;
	delete: FieldMetadata<string>;
	status: FieldMetadata<CaisseStatusEnumVd>;
};

export interface ConversionBean extends AbstractBean {
	unite: UniteBean;
	facteur: FieldMetadata<number>;
};

export interface UniteBean extends AbstractBean {
	description: FieldMetadata<string>;
	uniteCode: FieldMetadata<string>;
};

export interface CaisseSearchBean extends AbstractBean {
	journeeDebut: FieldMetadata<any>;
	Partner: FieldMetadata<string>;
	magasin: FieldMetadata<string>;
	status: FieldMetadata<CaisseStatusEnumVd>;
	journeeFin: FieldMetadata<any>;
};

export interface OperationCaisseBean extends AbstractLigneBean {
	dateOperation: FieldMetadata<any>;
	heureOperation: FieldMetadata<any>;
	supprimer: FieldMetadata<string>;
	typeOperation: FieldMetadata<OperationTypeEnumVd>;
};

export interface CasierBean extends AbstractBean {
	casierCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
};

export interface MagasinSearchBean extends AbstractBean {
	magasinCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
};

export interface CaisseIdBean {
	journee: any;
	Partner: string;
	magasin: string;
};

export interface UniteSearchBean extends AbstractBean {
	description: FieldMetadata<string>;
	uniteCode: FieldMetadata<string>;
};

export interface MagasinBean extends AbstractBean {
	magasinCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
	casiers: Array<CasierBean>;
};

export interface ArticleBean extends AbstractBean {
	unite: UniteBean;
	conversions: Array<ConversionBean>;
	variants: Array<VariantBean>;
	articleCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
	type: FieldMetadata<ArticleTypeEnumVd>;
};

export interface VariantBean extends AbstractBean {
	variantCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
};

export interface ArticleSearchBean extends AbstractBean {
	articleCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
	type: FieldMetadata<ArticleTypeEnumVd>;
};