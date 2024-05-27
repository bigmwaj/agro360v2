import { FieldMetadata } from './metadata';
import { CommandeStatusEnumVd, CommandeTypeEnumVd, FactureStatusEnumVd, FactureTypeEnumVd, LigneTypeEnumVd, PaiementInteracViaEnumVd, ReceptionStatusEnumVd, RemiseTypeEnumVd, RetourStatusEnumVd } from './vd.av';
import { AbstractBean, AbstractSearchBean, AbstractStatusTrackingBean } from './bean.common';
import { PartnerBean } from './bean.core';
import { CompteBean, TaxeBean, TransactionBean } from './bean.finance';
import { ArticleBean, MagasinBean, UniteBean } from './bean.stock';

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
	receptionBtn: FieldMetadata<string>;
	ville: FieldMetadata<string>;
	dateDebut: FieldMetadata<any>;
	partner: FieldMetadata<string>;
	dateFin: FieldMetadata<any>;
	type: FieldMetadata<CommandeTypeEnumVd>;
	retourBtn: FieldMetadata<string>;
};

export interface CommandeBean extends AbstractStatusTrackingBean<CommandeStatusEnumVd> {
	magasin: MagasinBean;
	annulerBtn: FieldMetadata<string>;
	cumulPaiement: FieldMetadata<number>;
	prixTotalHT: FieldMetadata<number>;
	lignes: Array<LigneBean>;
	description: FieldMetadata<string>;
	partner: PartnerBean;
	createLigneBtn: FieldMetadata<string>;
	retourBtn: FieldMetadata<string>;
	terminerBtn: FieldMetadata<string>;
	commandeCode: FieldMetadata<string>;
	remise: FieldMetadata<number>;
	receptionBtn: FieldMetadata<string>;
	taxe: FieldMetadata<number>;
	reglerBtn: FieldMetadata<string>;
	status: FieldMetadata<CommandeStatusEnumVd>;
	date: FieldMetadata<any>;
	type: FieldMetadata<CommandeTypeEnumVd>;
	prixTotal: FieldMetadata<number>;
};

export interface ReglementCommandeBean extends AbstractBean {
	commandeCode: FieldMetadata<string>;
	reglementId: FieldMetadata<number>;
	transaction: TransactionBean;
};

export interface FactureSearchBean extends AbstractSearchBean {
	statusIn: FieldMetadata<any>;
	ville: FieldMetadata<string>;
	dateDebut: FieldMetadata<any>;
	partner: FieldMetadata<string>;
	dateFin: FieldMetadata<any>;
	factureCode: FieldMetadata<string>;
	type: FieldMetadata<FactureTypeEnumVd>;
};

export interface FactureTaxeBean extends AbstractBean {
	montant: FieldMetadata<number>;
	taux: FieldMetadata<number>;
	taxe: TaxeBean;
};

export interface ReglementBean extends AbstractBean {
	facture: FactureBean;
	transaction: TransactionBean;
	type: any;
};

export interface PaiementParamBean extends AbstractBean {
	phone: FieldMetadata<string>;
	interacVia: FieldMetadata<PaiementInteracViaEnumVd>;
	montant: FieldMetadata<number>;
	email: FieldMetadata<string>;
	compte: CompteBean;
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
	remiseMontant: FieldMetadata<number>;
	remiseRaison: FieldMetadata<string>;
	unite: UniteBean;
	remise: FieldMetadata<number>;
	taxe: FieldMetadata<number>;
	ligneId: FieldMetadata<number>;
	type: FieldMetadata<LigneTypeEnumVd>;
	prixTotal: FieldMetadata<number>;
};

export interface EtatDetteBean {
	montant: FieldMetadata<number>;
	type: FieldMetadata<FactureTypeEnumVd>;
	libelle: FieldMetadata<string>;
};

export interface FactureBean extends AbstractStatusTrackingBean<FactureStatusEnumVd> {
	cumulPaiement: FieldMetadata<number>;
	prixTotalHT: FieldMetadata<number>;
	status: FieldMetadata<FactureStatusEnumVd>;
	description: FieldMetadata<string>;
	partner: PartnerBean;
	type: FieldMetadata<FactureTypeEnumVd>;
	compte: CompteBean;
	remise: FieldMetadata<number>;
	taxe: FieldMetadata<number>;
	reglerBtn: FieldMetadata<string>;
	factureCode: FieldMetadata<string>;
	date: FieldMetadata<any>;
	prixTotal: FieldMetadata<number>;
	commande: CommandeBean;
};

export interface LigneTaxeBean extends AbstractBean {
	commandeCode: FieldMetadata<string>;
	montant: FieldMetadata<number>;
	ligneId: FieldMetadata<number>;
	taux: FieldMetadata<number>;
	taxe: TaxeBean;
};