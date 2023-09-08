import { FieldMetadata } from './metadata';
import { PhaseEnumVd, RubriqueEnumVd, StatusCycleEnumVd } from './vd.production.avicole';
import { AbstractBean, AbstractLigneBean, AbstractStatusTrackingBean } from './bean.common';
import { MagasinBean } from './bean.stock';

export interface JourneeBean extends AbstractBean {
	cycle: CycleBean;
	numeroJournee: FieldMetadata<number>;
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
	phase: FieldMetadata<PhaseEnumVd>;
};