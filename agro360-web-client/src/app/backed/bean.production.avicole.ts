import { FieldMetadata } from './metadata';
import { CycleStatusEnumVd, PhaseEnumVd, ProductionCategoryEnumVd, RubriqueEnumVd } from './vd.production.avicole';
import { AbstractBean, AbstractLigneBean, AbstractStatusTrackingBean } from './bean.common';
import { ArticleBean, MagasinBean, UniteBean, VariantBean } from './bean.stock';

export interface CycleSearchBean extends AbstractBean {
	cycleCode: FieldMetadata<string>;
	magasin: FieldMetadata<string>;
	status: FieldMetadata<CycleStatusEnumVd>;
};

export interface ProductionBean extends AbstractBean {
	unite: UniteBean;
	category: FieldMetadata<ProductionCategoryEnumVd>;
	quantite: FieldMetadata<number>;
	productionId: FieldMetadata<number>;
	commentaire: FieldMetadata<string>;
	numeroJournee: FieldMetadata<number>;
	article: ArticleBean;
	uniteLibelle: FieldMetadata<string>;
	variant: VariantBean;
	alveole: FieldMetadata<number>;
};

export interface CycleBean extends AbstractStatusTrackingBean<CycleStatusEnumVd> {
	magasin: MagasinBean;
	cycleCode: FieldMetadata<string>;
	racePlanifiee: FieldMetadata<string>;
	quantiteEffective: FieldMetadata<number>;
	raceEffective: FieldMetadata<string>;
	description: FieldMetadata<string>;
	quantitePlanifiee: FieldMetadata<number>;
	dateEffective: FieldMetadata<any>;
	datePlanifiee: FieldMetadata<any>;
	metadatas: Array<MetadataBean>;
	status: FieldMetadata<CycleStatusEnumVd>;
};

export interface JourneeSearchBean extends AbstractBean {
	journee: FieldMetadata<any>;
	cycleCode: FieldMetadata<string>;
};

export interface OperationSearchBean extends AbstractBean {
	phase: FieldMetadata<PhaseEnumVd>;
	cycleCode: FieldMetadata<string>;
	rubrique: FieldMetadata<RubriqueEnumVd>;
};

export interface JourneeBean extends AbstractBean {
	journee: FieldMetadata<any>;
	numeroJournee: FieldMetadata<number>;
	productions: Array<ProductionBean>;
	cycle: CycleBean;
};

export interface MetadataBean extends AbstractBean {
	metadataCode: FieldMetadata<string>;
	value: FieldMetadata<string>;
	type: FieldMetadata<string>;
	description: FieldMetadata<string>;
	ordre: FieldMetadata<number>;
	libelle: FieldMetadata<string>;
};

export interface OperationBean extends AbstractLigneBean {
	phase: FieldMetadata<PhaseEnumVd>;
	numeroJournee: FieldMetadata<number>;
	rubrique: FieldMetadata<RubriqueEnumVd>;
};

export interface ProductionSearchBean extends AbstractBean {
	cycleCode: FieldMetadata<string>;
};