import { FieldMetadata } from './metadata';
import { PhaseEnumVd, RubriqueEnumVd, StatusCycleEnumVd } from './vd.production.avicole';
import { AbstractBean, AbstractLigneBean, AbstractStatusTrackingFormBean } from './bean.common';
import { MagasinBean } from './bean.stock';

export interface CycleBean extends AbstractStatusTrackingFormBean<StatusCycleEnumVd> {
	description: FieldMetadata<string>;
	dateEffective: FieldMetadata<any>;
	quantitePlanifiee: FieldMetadata<number>;
	status: FieldMetadata<StatusCycleEnumVd>;
	articleCode: FieldMetadata<string>;
	magasin: MagasinBean;
	datePlanifiee: FieldMetadata<any>;
	cycleCode: FieldMetadata<string>;
	quantiteEffective: FieldMetadata<number>;
};

export interface OperationBean extends AbstractLigneBean {
	rubrique: FieldMetadata<RubriqueEnumVd>;
	phase: FieldMetadata<PhaseEnumVd>;
};

export interface JourneeBean extends AbstractBean {
	cycle: CycleBean;
	numeroJournee: FieldMetadata<number>;
};

export interface MetadataBean extends AbstractBean {
	cycle: CycleBean;
	value: FieldMetadata<string>;
	metadataCode: FieldMetadata<string>;
};