import { FieldMetadata } from './metadata';
import { TransactionStatusEnumVd, TransactionTypeEnumVd } from './vd.finance';
import { AbstractBean, AbstractStatusTrackingBean } from './bean.common';
import { PartnerBean } from './bean.core';

export interface TransfertBean extends AbstractBean {
	compteSource: CompteBean;
	compteCible: CompteBean;
	montant: FieldMetadata<any>;
	partner: PartnerBean;
	date: FieldMetadata<any>;
	note: FieldMetadata<string>;
};

export interface TransactionSearchBean extends AbstractBean {
	rubrique: FieldMetadata<string>;
	compte: FieldMetadata<string>;
	type: FieldMetadata<TransactionTypeEnumVd>;
	transactionCode: FieldMetadata<string>;
	status: FieldMetadata<TransactionStatusEnumVd>;
	partner: FieldMetadata<string>;
};

export interface CompteBean extends AbstractBean {
	compteCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
};

export interface RubriqueSearchBean extends AbstractBean {
	type: FieldMetadata<TransactionTypeEnumVd>;
	rubriqueCode: FieldMetadata<string>;
};

export interface TransactionBean extends AbstractStatusTrackingBean<TransactionStatusEnumVd> {
	type: FieldMetadata<TransactionTypeEnumVd>;
	transactionCode: FieldMetadata<string>;
	montant: FieldMetadata<any>;
	status: FieldMetadata<TransactionStatusEnumVd>;
	rubrique: RubriqueBean;
	partner: PartnerBean;
	date: FieldMetadata<any>;
	note: FieldMetadata<string>;
	compte: CompteBean;
};

export interface RubriqueBean extends AbstractBean {
	type: FieldMetadata<TransactionTypeEnumVd>;
	rubriqueCode: FieldMetadata<string>;
	nom: FieldMetadata<string>;
	description: FieldMetadata<string>;
};

export interface CompteSearchBean extends AbstractBean {
	compteCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
};

export interface EtatCompteBean extends AbstractBean {
	compte: CompteBean;
	solde: FieldMetadata<any>;
};