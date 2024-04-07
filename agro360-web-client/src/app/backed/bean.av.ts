import { FieldMetadata } from './metadata';
import { ReceptionStatusEnumVd } from './vd.achat';
import { CommandeStatusEnumVd, CommandeTypeEnumVd, FactureStatusEnumVd, FactureTypeEnumVd, LigneTypeEnumVd } from './vd.av';
import { AbstractBean, AbstractStatusTrackingBean } from './bean.common';
import { PartnerBean } from './bean.core';
import { CompteBean, TransactionBean } from './bean.finance';
import { ArticleBean, MagasinBean, UniteBean } from './bean.stock';

export interface FactureBean extends AbstractStatusTrackingBean<FactureStatusEnumVd> {
	montant: FieldMetadata<any>;
	status: FieldMetadata<FactureStatusEnumVd>;
	description: FieldMetadata<string>;
	partner: PartnerBean;
	factureCode: FieldMetadata<string>;
	date: FieldMetadata<any>;
	type: FieldMetadata<FactureTypeEnumVd>;
	commande: CommandeBean;
};

export interface FactureSearchBean extends AbstractBean {
	statusIn: FieldMetadata<any>;
	dateDebut: FieldMetadata<any>;
	partner: FieldMetadata<string>;
	dateFin: FieldMetadata<any>;
	factureCode: FieldMetadata<string>;
};

export interface LigneBean extends AbstractBean {
	variantCode: FieldMetadata<string>;
	unite: UniteBean;
	quantite: FieldMetadata<number>;
	article: ArticleBean;
	numero: FieldMetadata<number>;
	description: FieldMetadata<string>;
	prixTotal: FieldMetadata<any>;
	ligneId: FieldMetadata<number>;
	type: FieldMetadata<LigneTypeEnumVd>;
	prixUnitaire: FieldMetadata<any>;
};

export interface CommandeSearchBean extends AbstractBean {
	statusIn: FieldMetadata<any>;
	commandeCode: FieldMetadata<string>;
	ville: FieldMetadata<string>;
	dateDebut: FieldMetadata<any>;
	partner: FieldMetadata<string>;
	dateFin: FieldMetadata<any>;
};

export interface ReglementFactureBean extends AbstractBean {
	reglementId: FieldMetadata<number>;
	montant: FieldMetadata<any>;
	transaction: TransactionBean;
	factureCode: FieldMetadata<string>;
};

export interface CommandeBean extends AbstractStatusTrackingBean<CommandeStatusEnumVd> {
	magasin: MagasinBean;
	commandeCode: FieldMetadata<string>;
	paiementComptant: FieldMetadata<any>;
	lignes: Array<LigneBean>;
	description: FieldMetadata<string>;
	prixTotal: FieldMetadata<any>;
	partner: PartnerBean;
	status: FieldMetadata<CommandeStatusEnumVd>;
	date: FieldMetadata<any>;
	type: FieldMetadata<CommandeTypeEnumVd>;
	compte: CompteBean;
};

export interface ReceptionBean extends AbstractStatusTrackingBean<ReceptionStatusEnumVd> {
	quantite: FieldMetadata<number>;
	description: FieldMetadata<string>;
	receptionId: FieldMetadata<number>;
	ligne: LigneBean;
	status: FieldMetadata<ReceptionStatusEnumVd>;
	prixUnitaire: FieldMetadata<any>;
	dateReception: FieldMetadata<any>;
};

export interface ReceptionInputBean extends AbstractBean {
	magasin: MagasinBean;
	quantite: FieldMetadata<number>;
	prixUnitaire: FieldMetadata<number>;
	casierCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
	ligne: LigneBean;
	dateReception: FieldMetadata<any>;
};

export interface ReglementCommandeBean extends AbstractBean {
	commandeCode: FieldMetadata<string>;
	reglementId: FieldMetadata<number>;
	montant: FieldMetadata<any>;
	transaction: TransactionBean;
};