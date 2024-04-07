import { FieldMetadata } from './metadata';
import { BonCommandeStatusEnumVd, ReceptionStatusEnumVd } from './vd.achat';
import { AbstractBean, AbstractLigneBean, AbstractStatusTrackingBean } from './bean.common';
import { PartnerBean } from './bean.core';
import { MagasinBean } from './bean.stock';

export interface BonCommandeSearchBean extends AbstractBean {
	statusIn: FieldMetadata<any>;
	dateBonCommandeFin: FieldMetadata<any>;
	fournisseur: FieldMetadata<string>;
	dateBonCommandeDebut: FieldMetadata<any>;
	ville: FieldMetadata<string>;
	bonCommandeCode: FieldMetadata<string>;
};

export interface LigneBean extends AbstractLigneBean {

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

export interface ReceptionBean extends AbstractStatusTrackingBean<ReceptionStatusEnumVd> {
	magasin: MagasinBean;
	quantite: FieldMetadata<number>;
	casierCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
	ligne: LigneBean;
	receptionId: FieldMetadata<number>;
	status: FieldMetadata<ReceptionStatusEnumVd>;
	prixUnitaire: FieldMetadata<any>;
	dateReception: FieldMetadata<any>;
};

export interface BonCommandeBean extends AbstractStatusTrackingBean<BonCommandeStatusEnumVd> {
	magasin: MagasinBean;
	fournisseur: PartnerBean;
	status: FieldMetadata<BonCommandeStatusEnumVd>;
	dateBonCommande: FieldMetadata<any>;
	lignes: Array<LigneBean>;
	plusAchetes: FieldMetadata<string>;
	description: FieldMetadata<string>;
	bonCommandeCode: FieldMetadata<string>;
	livraison: FieldMetadata<boolean>;
	prixTotal: FieldMetadata<number>;
};