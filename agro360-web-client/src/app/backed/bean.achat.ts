import { AbstractBean, AbstractFormBean, AbstractLigneBean } from './bean.common';
import { MagasinBean } from './bean.stock';
import { TiersBean } from './bean.tiers';
import { FieldMetadata } from './metadata';
import { StatutBonCommandeEnumVd, StatutReceptionEnumVd } from './vd.achat';

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

export interface LigneBean extends AbstractLigneBean {

};

export interface BonCommandeBean extends AbstractFormBean {
	bonCommandeCode: FieldMetadata<string>;
	dateBonCommande: FieldMetadata<any>;
	description: FieldMetadata<string>;
	lignes: Array<LigneBean>;
	magasin: MagasinBean;
	statut: FieldMetadata<StatutBonCommandeEnumVd>;
	fournisseur: TiersBean;
	livraison: FieldMetadata<boolean>;
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