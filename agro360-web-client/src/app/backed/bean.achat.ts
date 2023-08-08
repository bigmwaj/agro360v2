import { FieldMetadata } from './metadata';
import { StatutBonCommandeEnumVd, StatutReceptionEnumVd } from './vd.achat';
import { AbstractBean, AbstractFormBean, AbstractLigneBean } from './bean.common';
import { TiersBean } from './bean.tiers';
import { MagasinBean } from './bean.stock';

export interface LigneBean extends AbstractLigneBean {

};

export interface ReceptionBean extends AbstractFormBean {
	description: FieldMetadata<string>;
	casierCode: FieldMetadata<string>;
	quantite: FieldMetadata<number>;
	receptionId: FieldMetadata<number>;
	dateReception: FieldMetadata<any>;
	magasin: MagasinBean;
	prixUnitaire: FieldMetadata<number>;
	ligne: LigneBean;
	statut: FieldMetadata<StatutReceptionEnumVd>;
};

export interface BonCommandeBean extends AbstractFormBean {
	bonCommandeCode: FieldMetadata<string>;
	dateBonCommande: FieldMetadata<any>;
	description: FieldMetadata<string>;
	lignes: Array<LigneBean>;
	statut: FieldMetadata<StatutBonCommandeEnumVd>;
	fournisseur: TiersBean;
	adresse: FieldMetadata<string>;
	livraison: FieldMetadata<boolean>;
	ville: FieldMetadata<string>;
};

export interface ReceptionInputBean extends AbstractFormBean {
	description: FieldMetadata<string>;
	casierCode: FieldMetadata<string>;
	quantite: FieldMetadata<number>;
	dateReception: FieldMetadata<any>;
	magasin: MagasinBean;
	prixUnitaire: FieldMetadata<number>;
	ligne: LigneBean;
};

export interface BonCommandeSearchBean extends AbstractBean {
	bonCommandeCode: FieldMetadata<string>;
	dateBonCommandeDebut: FieldMetadata<any>;
	dateBonCommandeFin: FieldMetadata<any>;
	statutDans: FieldMetadata<any>;
	fournisseur: FieldMetadata<string>;
	ville: FieldMetadata<string>;
};