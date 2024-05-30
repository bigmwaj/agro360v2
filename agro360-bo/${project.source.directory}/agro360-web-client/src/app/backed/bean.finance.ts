import { FieldMetadata } from './metadata';
import { CompteTypeEnumVd, TransactionStatusEnumVd, TransactionTypeEnumVd } from './vd.finance';
import { AbstractBean, AbstractSearchBean, AbstractStatusTrackingBean } from './bean.common';
import { PartnerBean } from './bean.core';

export interface EtatRecetteDepenseBean {
	depense: FieldMetadata<number>;
	semaine: FieldMetadata<any>;
	recette: FieldMetadata<number>;
};

export interface RubriqueSearchBean extends AbstractSearchBean {
	rubrique: FieldMetadata<string>;
	type: FieldMetadata<TransactionTypeEnumVd>;
};

export interface TransfertBean extends AbstractBean {
	compteSource: CompteBean;
	compteCible: CompteBean;
	montant: FieldMetadata<number>;
	partner: PartnerBean;
	date: FieldMetadata<any>;
	note: FieldMetadata<string>;
};

export interface RubriqueBean extends AbstractBean {
	type: FieldMetadata<TransactionTypeEnumVd>;
	rubriqueCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
	libelle: FieldMetadata<string>;
};

export interface EtatCompteBean extends AbstractBean {
	solde: FieldMetadata<number>;
	compte: CompteBean;
};

export interface CompteSearchBean extends AbstractSearchBean {
	compte: FieldMetadata<string>;
	type: FieldMetadata<CompteTypeEnumVd>;
	partner: FieldMetadata<string>;
};

export interface TaxeBean extends AbstractBean {
	taxeCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
	taux: FieldMetadata<number>;
};

export interface TransactionBean extends AbstractStatusTrackingBean<TransactionStatusEnumVd> {
	type: FieldMetadata<TransactionTypeEnumVd>;
	transactionCode: FieldMetadata<string>;
	status: FieldMetadata<TransactionStatusEnumVd>;
	rubrique: RubriqueBean;
	montant: FieldMetadata<number>;
	partner: PartnerBean;
	date: FieldMetadata<any>;
	note: FieldMetadata<string>;
	compte: CompteBean;
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

export interface CompteBean extends AbstractBean {
	type: FieldMetadata<CompteTypeEnumVd>;
	compteCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
	partner: PartnerBean;
	libelle: FieldMetadata<string>;
};