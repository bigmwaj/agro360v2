import { FieldMetadata } from './metadata';
import { CommandeStatusEnumVd } from './vd.vente';
import { AbstractBean, AbstractLigneBean, AbstractStatusTrackingBean } from './bean.common';
import { PartnerBean } from './bean.core';
import { MagasinBean } from './bean.stock';

export interface CommandeSearchBean extends AbstractBean {
	statusIn: FieldMetadata<any>;
	commandeCode: FieldMetadata<string>;
	client: FieldMetadata<string>;
	ville: FieldMetadata<string>;
	dateDebut: FieldMetadata<any>;
	dateFin: FieldMetadata<any>;
};

export interface CommandeBean extends AbstractStatusTrackingBean<CommandeStatusEnumVd> {
	magasin: MagasinBean;
	livree: FieldMetadata<boolean>;
	transportRequis: FieldMetadata<boolean>;
	description: FieldMetadata<string>;
	adresse: FieldMetadata<string>;
	plusVendus: FieldMetadata<string>;
	commandeCode: FieldMetadata<string>;
	lignes: Array<LigneBean>;
	status: FieldMetadata<CommandeStatusEnumVd>;
	ville: FieldMetadata<string>;
	date: FieldMetadata<any>;
	prixTotal: FieldMetadata<number>;
	client: PartnerBean;
};

export interface LigneBean extends AbstractLigneBean {
	magasin: MagasinBean;
	casierCode: FieldMetadata<string>;
	nonFacturable: FieldMetadata<boolean>;
	nonCartonnee: FieldMetadata<boolean>;
	nonEmballee: FieldMetadata<boolean>;
};