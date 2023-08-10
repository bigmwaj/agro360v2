import { FieldMetadata } from './metadata';
import { StatutCommandeEnumVd } from './vd.vente';
import { AbstractBean, AbstractFormBean, AbstractLigneBean } from './bean.common';
import { TiersBean } from './bean.tiers';
import { MagasinBean } from './bean.stock';

export interface CommandeSearchBean extends AbstractBean {
	commandeCode: FieldMetadata<string>;
	statutDans: FieldMetadata<any>;
	client: FieldMetadata<string>;
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

export interface CommandeBean extends AbstractFormBean {
	description: FieldMetadata<string>;
	commandeCode: FieldMetadata<string>;
	dateCommande: FieldMetadata<any>;
	livree: FieldMetadata<boolean>;
	magasin: MagasinBean;
	client: TiersBean;
	statut: FieldMetadata<StatutCommandeEnumVd>;
	adresse: FieldMetadata<string>;
	lignes: Array<LigneBean>;
	transportRequis: FieldMetadata<boolean>;
	ville: FieldMetadata<string>;
};