import { FieldMetadata } from './metadata';
import { TransactionStatusEnumVd, TransactionTypeEnumVd } from './vd.finance';
import { AbstractBean, AbstractSearchBean, AbstractStatusTrackingBean } from './bean.common';
import { PartnerBean } from './bean.core';

export interface TransactionBean extends AbstractStatusTrackingBean<TransactionStatusEnumVd> {
	deleteBtn: FieldMetadata<string>;
	type: FieldMetadata<TransactionTypeEnumVd>;
	transactionCode: FieldMetadata<string>;
	accompte: FieldMetadata<boolean>;
	status: FieldMetadata<TransactionStatusEnumVd>;
	rubrique: RubriqueBean;
	montant: FieldMetadata<number>;
	partner: PartnerBean;
	date: FieldMetadata<any>;
	note: FieldMetadata<string>;
	saveBtn: FieldMetadata<string>;
	compte: CompteBean;
};

export interface CompteSearchBean extends AbstractSearchBean {
	compteCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
};

export interface CompteBean extends AbstractBean {
	compteCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
};

export interface TransactionSearchBean extends AbstractSearchBean {
	statusIn: FieldMetadata<any>;
	rubrique: FieldMetadata<string>;
	compte: FieldMetadata<string>;
	rubriqueBtn: FieldMetadata<string>;
	type: FieldMetadata<TransactionTypeEnumVd>;
	transactionCode: FieldMetadata<string>;
	compteBtn: FieldMetadata<string>;
	dateDebut: FieldMetadata<any>;
	taxeBtn: FieldMetadata<string>;
	dateFin: FieldMetadata<any>;
	partner: FieldMetadata<string>;
	transfertBtn: FieldMetadata<string>;
};

export interface RubriqueBean extends AbstractBean {
	type: FieldMetadata<TransactionTypeEnumVd>;
	rubriqueCode: FieldMetadata<string>;
	nom: FieldMetadata<string>;
	description: FieldMetadata<string>;
};

export interface TaxeBean extends AbstractBean {
	taxeCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
	taux: FieldMetadata<number>;
};

export interface TransfertBean extends AbstractBean {
	compteSource: CompteBean;
	compteCible: CompteBean;
	montant: FieldMetadata<number>;
	partner: PartnerBean;
	date: FieldMetadata<any>;
	note: FieldMetadata<string>;
};

export interface EtatCompteBean extends AbstractBean {
	solde: FieldMetadata<number>;
	compte: CompteBean;
};

export interface RubriqueSearchBean extends AbstractSearchBean {
	type: FieldMetadata<TransactionTypeEnumVd>;
	rubriqueCode: FieldMetadata<string>;
};