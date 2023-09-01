import { FieldMetadata } from './metadata';
import { StatusCommandeEnumVd } from './vd.vente';
import { AbstractBean, AbstractLigneBean, AbstractStatusTrackingFormBean } from './bean.common';
import { TiersBean } from './bean.tiers';
import { MagasinBean } from './bean.stock';

export interface CommandeBean extends AbstractStatusTrackingFormBean<StatusCommandeEnumVd> {
	status: FieldMetadata<StatusCommandeEnumVd>;
	description: FieldMetadata<string>;
	prixTotal: FieldMetadata<number>;
	commandeCode: FieldMetadata<string>;
	dateCommande: FieldMetadata<any>;
	livree: FieldMetadata<boolean>;
	magasin: MagasinBean;
	client: TiersBean;
	adresse: FieldMetadata<string>;
	lignes: Array<LigneBean>;
	transportRequis: FieldMetadata<boolean>;
	ville: FieldMetadata<string>;
};

export interface LigneBean extends AbstractLigneBean {
	nonFacturable: FieldMetadata<boolean>;
	casierCode: FieldMetadata<string>;
	nonEmballee: FieldMetadata<boolean>;
	magasin: MagasinBean;
	nonCartonnee: FieldMetadata<boolean>;
};

export interface CommandeSearchBean extends AbstractBean {
	commandeCode: FieldMetadata<string>;
	client: FieldMetadata<string>;
	statusIn: FieldMetadata<any>;
	dateCommandeFin: FieldMetadata<any>;
	dateCommandeDebut: FieldMetadata<any>;
	ville: FieldMetadata<string>;
};