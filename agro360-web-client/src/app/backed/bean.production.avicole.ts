import { FieldMetadata } from './metadata';
import { PhaseEnumVd, ProductionCategoryEnumVd, RubriqueEnumVd, StatusCycleEnumVd } from './vd.production.avicole';
import { AbstractBean, AbstractLigneBean, AbstractStatusTrackingBean } from './bean.common';
import { ArticleBean, MagasinBean, UniteBean, VariantBean } from './bean.stock';

export interface JourneeSearchBean extends AbstractBean {
	journee: FieldMetadata<any>;
	cycleCode: FieldMetadata<string>;
};

export interface JourneeBean extends AbstractBean {
	cycle: CycleBean;
	numeroJournee: FieldMetadata<number>;
	journee: FieldMetadata<any>;
	productions: Array<ProductionBean>;
};

export interface OperationSearchBean extends AbstractBean {
	rubrique: FieldMetadata<RubriqueEnumVd>;
	phase: FieldMetadata<PhaseEnumVd>;
	cycleCode: FieldMetadata<string>;
};

export interface ProductionSearchBean extends AbstractBean {
	cycleCode: FieldMetadata<string>;
};

export interface MetadataBean extends AbstractBean {
	description: FieldMetadata<string>;
	ordre: FieldMetadata<number>;
	libelle: FieldMetadata<string>;
	value: FieldMetadata<string>;
	metadataCode: FieldMetadata<string>;
	type: FieldMetadata<string>;
};

export interface CycleSearchBean extends AbstractBean {
	status: FieldMetadata<StatusCycleEnumVd>;
	magasin: FieldMetadata<string>;
	cycleCode: FieldMetadata<string>;
};

export interface CycleBean extends AbstractStatusTrackingBean<StatusCycleEnumVd> {
	raceEffective: FieldMetadata<string>;
	description: FieldMetadata<string>;
	dateEffective: FieldMetadata<any>;
	quantitePlanifiee: FieldMetadata<number>;
	status: FieldMetadata<StatusCycleEnumVd>;
	metadatas: Array<MetadataBean>;
	magasin: MagasinBean;
	datePlanifiee: FieldMetadata<any>;
	cycleCode: FieldMetadata<string>;
	racePlanifiee: FieldMetadata<string>;
	quantiteEffective: FieldMetadata<number>;
};

export interface OperationBean extends AbstractLigneBean {
	rubrique: FieldMetadata<RubriqueEnumVd>;
	numeroJournee: FieldMetadata<number>;
	phase: FieldMetadata<PhaseEnumVd>;
};

export interface ProductionBean extends AbstractBean {
	unite: UniteBean;
	uniteLibelle: FieldMetadata<string>;
	quantite: FieldMetadata<number>;
	productionId: FieldMetadata<number>;
	numeroJournee: FieldMetadata<number>;
	article: ArticleBean;
	variant: VariantBean;
	commentaire: FieldMetadata<string>;
	alveole: FieldMetadata<number>;
	category: FieldMetadata<ProductionCategoryEnumVd>;
};