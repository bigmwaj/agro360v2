import { FieldMetadata } from './metadata';
import { CompteTypeEnumVd, TransactionStatusEnumVd, TransactionTypeEnumVd } from './vd.finance';
import { AbstractBean, AbstractSearchBean, AbstractStatusTrackingBean } from './bean.common';
import { PartnerBean } from './bean.core';

export interface CompteBean extends AbstractBean {
	compteCode: FieldMetadata<any>;
	type: FieldMetadata<CompteTypeEnumVd>;
	partner: PartnerBean;
	description: FieldMetadata<any>;
	libelle: FieldMetadata<any>;
};

export interface TransactionSearchBean extends AbstractSearchBean {
	statusIn: FieldMetadata<any>;
	rubrique: FieldMetadata<any>;
	compte: FieldMetadata<any>;
	type: FieldMetadata<TransactionTypeEnumVd>;
	rubriqueBtn: FieldMetadata<any>;
	compteBtn: FieldMetadata<any>;
	dateDebut: FieldMetadata<any>;
	dateFin: FieldMetadata<any>;
	partner: FieldMetadata<any>;
	transfertBtn: FieldMetadata<any>;
	transactionCode: FieldMetadata<any>;
	taxeBtn: FieldMetadata<any>;
};

export interface RubriqueBean extends AbstractBean {
	type: FieldMetadata<TransactionTypeEnumVd>;
	rubriqueCode: FieldMetadata<any>;
	description: FieldMetadata<any>;
	libelle: FieldMetadata<any>;
};

export interface TaxeBean extends AbstractBean {
	taux: FieldMetadata<any>;
	description: FieldMetadata<any>;
	taxeCode: FieldMetadata<any>;
};

export interface EtatCompteBean extends AbstractBean {
	compte: CompteBean;
	solde: FieldMetadata<any>;
};

export interface TransactionBean extends AbstractStatusTrackingBean<TransactionStatusEnumVd> {
	type: FieldMetadata<TransactionTypeEnumVd>;
	montant: FieldMetadata<any>;
	status: FieldMetadata<TransactionStatusEnumVd>;
	rubrique: RubriqueBean;
	note: FieldMetadata<any>;
	partner: PartnerBean;
	date: FieldMetadata<any>;
	transactionCode: FieldMetadata<any>;
	compte: CompteBean;
};

export interface EtatRecetteDepenseBean {
	depense: FieldMetadata<any>;
	recette: FieldMetadata<any>;
	semaine: FieldMetadata<any>;
};

export interface TransfertBean extends AbstractBean {
	compteSource: CompteBean;
	compteCible: CompteBean;
	montant: FieldMetadata<any>;
	note: FieldMetadata<any>;
	partner: PartnerBean;
	date: FieldMetadata<any>;
};

export interface CompteSearchBean extends AbstractSearchBean {
	compte: FieldMetadata<any>;
	type: FieldMetadata<CompteTypeEnumVd>;
	partner: FieldMetadata<any>;
};

export interface RubriqueSearchBean extends AbstractSearchBean {
	rubrique: FieldMetadata<any>;
	type: FieldMetadata<TransactionTypeEnumVd>;
};