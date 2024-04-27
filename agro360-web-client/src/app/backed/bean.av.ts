import { FieldMetadata } from './metadata';
import { CommandeStatusEnumVd, CommandeTypeEnumVd, FactureStatusEnumVd, FactureTypeEnumVd, LigneTypeEnumVd, ReceptionStatusEnumVd, RemiseTypeEnumVd, RetourStatusEnumVd } from './vd.av';
import { AbstractBean, AbstractSearchBean, AbstractStatusTrackingBean } from './bean.common';
import { PartnerBean } from './bean.core';
import { CompteBean, TaxeBean, TransactionBean } from './bean.finance';
import { ArticleBean, MagasinBean, UniteBean } from './bean.stock';

export interface ReglementCommandeBean extends AbstractBean {
	commandeCode: FieldMetadata<string>;
	reglementId: FieldMetadata<number>;
	transaction: TransactionBean;
};

export interface ReglementFactureBean extends AbstractBean {
	reglementId: FieldMetadata<number>;
	transaction: TransactionBean;
	montant: FieldMetadata<number>;
	factureCode: FieldMetadata<string>;
};

export interface ReceptionLigneBean extends AbstractStatusTrackingBean<ReceptionStatusEnumVd> {
	unite: UniteBean;
	quantite: FieldMetadata<number>;
	prixUnitaire: FieldMetadata<number>;
	description: FieldMetadata<string>;
	receptionId: FieldMetadata<number>;
	date: FieldMetadata<any>;
	ligne: LigneBean;
	status: FieldMetadata<ReceptionStatusEnumVd>;
};

export interface LigneBean extends AbstractBean {
	variantCode: FieldMetadata<string>;
	quantite: FieldMetadata<number>;
	prixUnitaire: FieldMetadata<number>;
	prixTotalHT: FieldMetadata<number>;
	article: ArticleBean;
	remiseTaux: FieldMetadata<number>;
	description: FieldMetadata<string>;
	remiseType: FieldMetadata<RemiseTypeEnumVd>;
	retourBtn: FieldMetadata<string>;
	remiseMontant: FieldMetadata<number>;
	remiseRaison: FieldMetadata<string>;
	unite: UniteBean;
	remise: FieldMetadata<number>;
	receptionBtn: FieldMetadata<string>;
	taxe: FieldMetadata<number>;
	ligneId: FieldMetadata<number>;
	type: FieldMetadata<LigneTypeEnumVd>;
	prixTotal: FieldMetadata<number>;
};

export interface FactureSearchBean extends AbstractSearchBean {
	statusIn: FieldMetadata<any>;
	createFactureBtn: FieldMetadata<string>;
	dateDebut: FieldMetadata<any>;
	partner: FieldMetadata<string>;
	dateFin: FieldMetadata<any>;
	factureCode: FieldMetadata<string>;
	type: FieldMetadata<FactureTypeEnumVd>;
};

export interface RetourLigneBean extends AbstractBean {
	unite: UniteBean;
	quantite: FieldMetadata<number>;
	prixUnitaire: FieldMetadata<number>;
	retourId: FieldMetadata<number>;
	description: FieldMetadata<string>;
	date: FieldMetadata<any>;
	status: FieldMetadata<RetourStatusEnumVd>;
	ligne: LigneBean;
};

export interface CommandeSearchBean extends AbstractSearchBean {
	statusIn: FieldMetadata<any>;
	commandeCode: FieldMetadata<string>;
	ville: FieldMetadata<string>;
	dateDebut: FieldMetadata<any>;
	partner: FieldMetadata<string>;
	dateFin: FieldMetadata<any>;
	type: FieldMetadata<CommandeTypeEnumVd>;
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

export interface LigneTaxeBean extends AbstractBean {
	montant: FieldMetadata<number>;
	taux: FieldMetadata<number>;
	taxe: TaxeBean;
};

export interface FactureBean extends AbstractStatusTrackingBean<FactureStatusEnumVd> {
	montant: FieldMetadata<number>;
	status: FieldMetadata<FactureStatusEnumVd>;
	description: FieldMetadata<string>;
	partner: PartnerBean;
	factureCode: FieldMetadata<string>;
	date: FieldMetadata<any>;
	type: FieldMetadata<FactureTypeEnumVd>;
	commande: CommandeBean;
};

export interface CommandeBean extends AbstractStatusTrackingBean<CommandeStatusEnumVd> {
	magasin: MagasinBean;
	paiementComptant: FieldMetadata<number>;
	cumulPaiement: FieldMetadata<number>;
	prixTotalHT: FieldMetadata<number>;
	lignes: Array<LigneBean>;
	description: FieldMetadata<string>;
	partner: PartnerBean;
	createLigneBtn: FieldMetadata<string>;
	encaissementBtn: FieldMetadata<string>;
	compte: CompteBean;
	commandeCode: FieldMetadata<string>;
	remise: FieldMetadata<number>;
	taxe: FieldMetadata<number>;
	status: FieldMetadata<CommandeStatusEnumVd>;
	date: FieldMetadata<any>;
	type: FieldMetadata<CommandeTypeEnumVd>;
	prixTotal: FieldMetadata<number>;
};