import { FieldMetadata } from './metadata';
import { PhaseEnumVd, RubriqueEnumVd, StatutCycleEnumVd } from './vd.production.avicole';
import { AbstractFormBean, AbstractLigneBean } from './bean.common';
import { MagasinBean } from './bean.stock';

export interface JourneeBean extends AbstractFormBean {
	cycle: CycleBean;
	numeroJournee: FieldMetadata<number>;
};

export interface MetadataBean extends AbstractFormBean {
	cycle: CycleBean;
	value: FieldMetadata<string>;
	metadataCode: FieldMetadata<string>;
};

export interface CycleBean extends AbstractFormBean {
	description: FieldMetadata<string>;
	dateEffective: FieldMetadata<any>;
	statut: FieldMetadata<StatutCycleEnumVd>;
	quantitePlanifiee: FieldMetadata<number>;
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