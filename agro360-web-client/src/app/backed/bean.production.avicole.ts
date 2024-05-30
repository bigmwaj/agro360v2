import { FieldMetadata } from './metadata';
import { CycleStatusEnumVd, PhaseEnumVd, ProductionCategoryEnumVd, RubriqueEnumVd } from './vd.production.avicole';
import { AbstractBean, AbstractStatusTrackingBean } from './bean.common';
import { ArticleBean, MagasinBean, UniteBean, VariantBean } from './bean.stock';

export interface MetadataBean extends AbstractBean {
	type: FieldMetadata<any>;
	metadataCode: FieldMetadata<any>;
	value: FieldMetadata<any>;
	description: FieldMetadata<any>;
	libelle: FieldMetadata<any>;
	ordre: FieldMetadata<any>;
};

export interface JourneeBean extends AbstractBean {
	journee: FieldMetadata<any>;
	numeroJournee: FieldMetadata<any>;
	productions: Array<ProductionBean>;
	cycle: CycleBean;
};

export interface ProductionBean extends AbstractBean {
	commentaire: FieldMetadata<any>;
	unite: UniteBean;
	numeroJournee: FieldMetadata<any>;
	category: FieldMetadata<ProductionCategoryEnumVd>;
	article: ArticleBean;
	productionId: FieldMetadata<any>;
	quantite: FieldMetadata<any>;
	variant: VariantBean;
	uniteLibelle: FieldMetadata<any>;
	alveole: FieldMetadata<any>;
};

export interface CycleSearchBean extends AbstractBean {
	cycleCode: FieldMetadata<any>;
	magasin: FieldMetadata<any>;
	status: FieldMetadata<CycleStatusEnumVd>;
};

export interface OperationSearchBean extends AbstractBean {
	phase: FieldMetadata<PhaseEnumVd>;
	cycleCode: FieldMetadata<any>;
	rubrique: FieldMetadata<RubriqueEnumVd>;
};

export interface ProductionSearchBean extends AbstractBean {
	cycleCode: FieldMetadata<any>;
};

export interface JourneeSearchBean extends AbstractBean {
	journee: FieldMetadata<any>;
	cycleCode: FieldMetadata<any>;
};

export interface CycleBean extends AbstractStatusTrackingBean<CycleStatusEnumVd> {
	magasin: MagasinBean;
	racePlanifiee: FieldMetadata<any>;
	quantiteEffective: FieldMetadata<any>;
	raceEffective: FieldMetadata<any>;
	cycleCode: FieldMetadata<any>;
	description: FieldMetadata<any>;
	dateEffective: FieldMetadata<any>;
	datePlanifiee: FieldMetadata<any>;
	metadatas: Array<MetadataBean>;
	status: FieldMetadata<CycleStatusEnumVd>;
	quantitePlanifiee: FieldMetadata<any>;
};