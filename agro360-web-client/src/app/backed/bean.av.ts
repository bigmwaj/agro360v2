import { FieldMetadata } from './metadata';
import { CommandeStatusEnumVd, CommandeTypeEnumVd, FactureStatusEnumVd, FactureTypeEnumVd, LigneTypeEnumVd, ReceptionStatusEnumVd, RemiseTypeEnumVd, RetourStatusEnumVd } from './vd.av';
import { AbstractBean, AbstractStatusTrackingBean } from './bean.common';
import { PartnerBean } from './bean.core';
import { CompteBean, TaxeBean, TransactionBean } from './bean.finance';
import { ArticleBean, MagasinBean, UniteBean } from './bean.stock';

export interface CommandeBean extends AbstractStatusTrackingBean<CommandeStatusEnumVd> {
	magasin: MagasinBean;
	paiementComptant: FieldMetadata<number>;
	prixTotalHT: FieldMetadata<number>;
	remiseTaux: FieldMetadata<number>;
	lignes: Array<LigneBean>;
	description: FieldMetadata<string>;
	partner: PartnerBean;
	remiseType: FieldMetadata<RemiseTypeEnumVd>;
	createLigneBtn: FieldMetadata<string>;
	saveBtn: FieldMetadata<string>;
	encaissementBtn: FieldMetadata<string>;
	compte: CompteBean;
	remiseMontant: FieldMetadata<number>;
	remiseRaison: FieldMetadata<string>;
	prixTotalTTC: FieldMetadata<number>;
	deleteBtn: FieldMetadata<string>;
	commandeCode: FieldMetadata<string>;
	remise: FieldMetadata<number>;
	taxe: FieldMetadata<number>;
	status: FieldMetadata<CommandeStatusEnumVd>;
	date: FieldMetadata<any>;
	type: FieldMetadata<CommandeTypeEnumVd>;
	prixTotal: FieldMetadata<number>;
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

export interface ReglementFactureBean extends AbstractBean {
	reglementId: FieldMetadata<number>;
	transaction: TransactionBean;
	montant: FieldMetadata<number>;
	factureCode: FieldMetadata<string>;
};

export interface ReglementCommandeBean extends AbstractBean {
	commandeCode: FieldMetadata<string>;
	reglementId: FieldMetadata<number>;
	transaction: TransactionBean;
	montant: FieldMetadata<number>;
};

export interface LigneTaxeBean extends AbstractBean {
	montant: FieldMetadata<number>;
	taux: FieldMetadata<number>;
	taxe: TaxeBean;
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

export interface FactureBean extends AbstractStatusTrackingBean<FactureStatusEnumVd> {
	deleteBtn: FieldMetadata<string>;
	montant: FieldMetadata<number>;
	status: FieldMetadata<FactureStatusEnumVd>;
	description: FieldMetadata<string>;
	partner: PartnerBean;
	factureCode: FieldMetadata<string>;
	date: FieldMetadata<any>;
	type: FieldMetadata<FactureTypeEnumVd>;
	commande: CommandeBean;
};

export interface CommandeSearchBean extends AbstractBean {
	statusIn: FieldMetadata<any>;
	commandeCode: FieldMetadata<string>;
	ville: FieldMetadata<string>;
	dateDebut: FieldMetadata<any>;
	partner: FieldMetadata<string>;
	dateFin: FieldMetadata<any>;
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
	quantite: FieldMetadata<number>;
	prixUnitaire: FieldMetadata<number>;
	prixTotalHT: FieldMetadata<number>;
	article: ArticleBean;
	remiseTaux: FieldMetadata<number>;
	description: FieldMetadata<string>;
	remiseType: FieldMetadata<RemiseTypeEnumVd>;
	retourBtn: FieldMetadata<string>;
	prixTotalTTC: FieldMetadata<number>;
	remiseMontant: FieldMetadata<number>;
	remiseRaison: FieldMetadata<string>;
	unite: UniteBean;
	deleteBtn: FieldMetadata<string>;
	receptionBtn: FieldMetadata<string>;
	taxe: FieldMetadata<number>;
	ligneId: FieldMetadata<number>;
	type: FieldMetadata<LigneTypeEnumVd>;
	prixTotal: FieldMetadata<number>;
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