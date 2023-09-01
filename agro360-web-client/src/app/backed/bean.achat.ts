import { FieldMetadata } from './metadata';
import { StatusBonCommandeEnumVd, StatusReceptionEnumVd } from './vd.achat';
import { AbstractBean, AbstractLigneBean, AbstractStatusTrackingFormBean } from './bean.common';
import { TiersBean } from './bean.tiers';
import { MagasinBean } from './bean.stock';

export interface LigneBean extends AbstractLigneBean {

};

export interface ReceptionBean extends AbstractStatusTrackingFormBean<StatusReceptionEnumVd> {
	status: FieldMetadata<StatusReceptionEnumVd>;
	description: FieldMetadata<string>;
	casierCode: FieldMetadata<string>;
	quantite: FieldMetadata<number>;
	receptionId: FieldMetadata<number>;
	dateReception: FieldMetadata<any>;
	magasin: MagasinBean;
	prixUnitaire: FieldMetadata<number>;
	ligne: LigneBean;
};

export interface ReceptionInputBean extends AbstractBean {
	description: FieldMetadata<string>;
	casierCode: FieldMetadata<string>;
	quantite: FieldMetadata<number>;
	dateReception: FieldMetadata<any>;
	magasin: MagasinBean;
	prixUnitaire: FieldMetadata<number>;
	ligne: LigneBean;
};

export interface BonCommandeBean extends AbstractStatusTrackingFormBean<StatusBonCommandeEnumVd> {
	bonCommandeCode: FieldMetadata<string>;
	dateBonCommande: FieldMetadata<any>;
	description: FieldMetadata<string>;
	prixTotal: FieldMetadata<number>;
	lignes: Array<LigneBean>;
	magasin: MagasinBean;
	status: FieldMetadata<StatusBonCommandeEnumVd>;
	fournisseur: TiersBean;
	livraison: FieldMetadata<boolean>;
};

export interface BonCommandeSearchBean extends AbstractBean {
	bonCommandeCode: FieldMetadata<string>;
	dateBonCommandeDebut: FieldMetadata<any>;
	dateBonCommandeFin: FieldMetadata<any>;
	fournisseur: FieldMetadata<string>;
	statusIn: FieldMetadata<any>;
	ville: FieldMetadata<string>;
};