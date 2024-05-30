import { FieldMetadata } from './metadata';
import { CommandeStatusEnumVd, CommandeTypeEnumVd, FactureStatusEnumVd, FactureTypeEnumVd, LigneTypeEnumVd, PaiementInteracViaEnumVd, ReceptionStatusEnumVd, RemiseTypeEnumVd, RetourStatusEnumVd } from './vd.av';
import { AbstractBean, AbstractSearchBean, AbstractStatusTrackingBean } from './bean.common';
import { PartnerBean } from './bean.core';
import { CompteBean, TaxeBean, TransactionBean } from './bean.finance';
import { ArticleBean, MagasinBean, UniteBean } from './bean.stock';

export interface CommandeBean extends AbstractStatusTrackingBean<CommandeStatusEnumVd> {
	magasin: MagasinBean;
	taxe: FieldMetadata<any>;
	remise: FieldMetadata<any>;
	receptionBtn: FieldMetadata<any>;
	lignes: Array<LigneBean>;
	prixTotalHT: FieldMetadata<any>;
	prixTotal: FieldMetadata<any>;
	partner: PartnerBean;
	description: FieldMetadata<any>;
	cumulPaiement: FieldMetadata<any>;
	annulerBtn: FieldMetadata<any>;
	retourBtn: FieldMetadata<any>;
	terminerBtn: FieldMetadata<any>;
	createLigneBtn: FieldMetadata<any>;
	status: FieldMetadata<CommandeStatusEnumVd>;
	date: FieldMetadata<any>;
	reglerBtn: FieldMetadata<any>;
	type: FieldMetadata<CommandeTypeEnumVd>;
	commandeCode: FieldMetadata<any>;
};

export interface ReglementCommandeBean extends AbstractBean {
	transaction: TransactionBean;
	reglementId: FieldMetadata<any>;
	commandeCode: FieldMetadata<any>;
};

export interface LigneBean extends AbstractBean {
	remiseRaison: FieldMetadata<any>;
	taxe: FieldMetadata<any>;
	remise: FieldMetadata<any>;
	article: ArticleBean;
	remiseMontant: FieldMetadata<any>;
	prixTotalHT: FieldMetadata<any>;
	prixTotal: FieldMetadata<any>;
	quantite: FieldMetadata<any>;
	remiseType: FieldMetadata<RemiseTypeEnumVd>;
	description: FieldMetadata<any>;
	ligneId: FieldMetadata<any>;
	unite: UniteBean;
	remiseTaux: FieldMetadata<any>;
	variantCode: FieldMetadata<any>;
	type: FieldMetadata<LigneTypeEnumVd>;
	prixUnitaire: FieldMetadata<any>;
};

export interface FactureTaxeBean extends AbstractBean {
	montant: FieldMetadata<any>;
	taux: FieldMetadata<any>;
	taxe: TaxeBean;
};

export interface PaiementParamBean extends AbstractBean {
	email: FieldMetadata<any>;
	interacVia: FieldMetadata<PaiementInteracViaEnumVd>;
	montant: FieldMetadata<any>;
	compte: CompteBean;
	phone: FieldMetadata<any>;
};

export interface EtatDetteBean {
	montant: FieldMetadata<any>;
	type: FieldMetadata<FactureTypeEnumVd>;
	libelle: FieldMetadata<any>;
};

export interface ReglementFactureBean extends AbstractBean {
	transaction: TransactionBean;
	reglementId: FieldMetadata<any>;
	factureCode: FieldMetadata<any>;
};

export interface ReceptionLigneBean extends AbstractStatusTrackingBean<ReceptionStatusEnumVd> {
	unite: UniteBean;
	receptionId: FieldMetadata<any>;
	quantite: FieldMetadata<any>;
	date: FieldMetadata<any>;
	description: FieldMetadata<any>;
	ligne: LigneBean;
	prixUnitaire: FieldMetadata<any>;
	status: FieldMetadata<ReceptionStatusEnumVd>;
};

export interface LigneTaxeBean extends AbstractBean {
	montant: FieldMetadata<any>;
	taux: FieldMetadata<any>;
	commandeCode: FieldMetadata<any>;
	taxe: TaxeBean;
	ligneId: FieldMetadata<any>;
};

export interface FactureBean extends AbstractStatusTrackingBean<FactureStatusEnumVd> {
	taxe: FieldMetadata<any>;
	remise: FieldMetadata<any>;
	prixTotalHT: FieldMetadata<any>;
	status: FieldMetadata<FactureStatusEnumVd>;
	prixTotal: FieldMetadata<any>;
	partner: PartnerBean;
	type: FieldMetadata<FactureTypeEnumVd>;
	description: FieldMetadata<any>;
	cumulPaiement: FieldMetadata<any>;
	compte: CompteBean;
	date: FieldMetadata<any>;
	reglerBtn: FieldMetadata<any>;
	factureCode: FieldMetadata<any>;
	commande: CommandeBean;
};

export interface CommandeSearchBean extends AbstractSearchBean {
	statusIn: FieldMetadata<any>;
	ville: FieldMetadata<any>;
	receptionBtn: FieldMetadata<any>;
	dateDebut: FieldMetadata<any>;
	dateFin: FieldMetadata<any>;
	partner: FieldMetadata<any>;
	type: FieldMetadata<CommandeTypeEnumVd>;
	commandeCode: FieldMetadata<any>;
	retourBtn: FieldMetadata<any>;
};

export interface ReglementBean extends AbstractBean {
	facture: FactureBean;
	transaction: TransactionBean;
	type: any;
};

export interface RetourLigneBean extends AbstractBean {
	unite: UniteBean;
	retourId: FieldMetadata<any>;
	quantite: FieldMetadata<any>;
	date: FieldMetadata<any>;
	status: FieldMetadata<RetourStatusEnumVd>;
	description: FieldMetadata<any>;
	ligne: LigneBean;
	prixUnitaire: FieldMetadata<any>;
};

export interface FactureSearchBean extends AbstractSearchBean {
	statusIn: FieldMetadata<any>;
	ville: FieldMetadata<any>;
	dateDebut: FieldMetadata<any>;
	dateFin: FieldMetadata<any>;
	partner: FieldMetadata<any>;
	type: FieldMetadata<FactureTypeEnumVd>;
	factureCode: FieldMetadata<any>;
};