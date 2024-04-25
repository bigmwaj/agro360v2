import { FieldMetadata } from './metadata';
import { TransactionStatusEnumVd, TransactionTypeEnumVd } from './vd.finance';
import { AbstractBean, AbstractStatusTrackingBean } from './bean.common';
import { PartnerBean } from './bean.core';

export interface TaxeBean extends AbstractBean {
	deleteBtn: FieldMetadata<string>;
	taxeCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
	taux: FieldMetadata<number>;
};

export interface EtatCompteBean extends AbstractBean {
	solde: FieldMetadata<number>;
	compte: CompteBean;
};

export interface CompteBean extends AbstractBean {
	deleteBtn: FieldMetadata<string>;
	compteCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
};

export interface TransfertBean extends AbstractBean {
	compteSource: CompteBean;
	compteCible: CompteBean;
	montant: FieldMetadata<number>;
	partner: PartnerBean;
	date: FieldMetadata<any>;
	note: FieldMetadata<string>;
};

export interface TransactionBean extends AbstractStatusTrackingBean<TransactionStatusEnumVd> {
	deleteBtn: FieldMetadata<string>;
	type: FieldMetadata<TransactionTypeEnumVd>;
	transactionCode: FieldMetadata<string>;
	status: FieldMetadata<TransactionStatusEnumVd>;
	rubrique: RubriqueBean;
	montant: FieldMetadata<number>;
	partner: PartnerBean;
	date: FieldMetadata<any>;
	note: FieldMetadata<string>;
	saveBtn: FieldMetadata<string>;
	compte: CompteBean;
};

export interface CompteSearchBean extends AbstractBean {
	compteCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
};

export interface RubriqueBean extends AbstractBean {
	deleteBtn: FieldMetadata<string>;
	type: FieldMetadata<TransactionTypeEnumVd>;
	rubriqueCode: FieldMetadata<string>;
	nom: FieldMetadata<string>;
	description: FieldMetadata<string>;
};

export interface RubriqueSearchBean extends AbstractBean {
	type: FieldMetadata<TransactionTypeEnumVd>;
	rubriqueCode: FieldMetadata<string>;
};

export interface TransactionSearchBean extends AbstractBean {
	statusIn: FieldMetadata<any>;
	rubrique: FieldMetadata<string>;
	compte: FieldMetadata<string>;
	dateDebut: FieldMetadata<any>;
	taxeBtn: FieldMetadata<string>;
	dateFin: FieldMetadata<any>;
	partner: FieldMetadata<string>;
	transfertBtn: FieldMetadata<string>;
	createTransactionBtn: FieldMetadata<string>;
	rubriqueBtn: FieldMetadata<string>;
	type: FieldMetadata<TransactionTypeEnumVd>;
	transactionCode: FieldMetadata<string>;
	compteBtn: FieldMetadata<string>;
};