import { FieldMetadata } from './metadata';
import { StatusCommandeEnumVd } from './vd.vente';
import { AbstractBean, AbstractLigneBean, AbstractStatusTrackingBean } from './bean.common';
import { TiersBean } from './bean.tiers';
import { MagasinBean } from './bean.stock';

export interface CommandeBean extends AbstractStatusTrackingBean<StatusCommandeEnumVd> {
	description: FieldMetadata<string>;
	plusVendus: FieldMetadata<string>;
	livree: FieldMetadata<boolean>;
	magasin: MagasinBean;
	client: TiersBean;
	adresse: FieldMetadata<string>;
	transportRequis: FieldMetadata<boolean>;
	ville: FieldMetadata<string>;
	status: FieldMetadata<StatusCommandeEnumVd>;
	prixTotal: FieldMetadata<number>;
	commandeCode: FieldMetadata<string>;
	dateCommande: FieldMetadata<any>;
	lignes: Array<LigneBean>;
};

export interface CommandeSearchBean extends AbstractBean {
	commandeCode: FieldMetadata<string>;
	client: FieldMetadata<string>;
	statusIn: FieldMetadata<any>;
	dateCommandeFin: FieldMetadata<any>;
	dateCommandeDebut: FieldMetadata<any>;
	ville: FieldMetadata<string>;
};

export interface LigneBean extends AbstractLigneBean {
	nonFacturable: FieldMetadata<boolean>;
	casierCode: FieldMetadata<string>;
	nonEmballee: FieldMetadata<boolean>;
	magasin: MagasinBean;
	nonCartonnee: FieldMetadata<boolean>;
};