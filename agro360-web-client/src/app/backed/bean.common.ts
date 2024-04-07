import { FieldMetadata } from './metadata';
import { ClientOperationEnumVd, EditActionEnumVd } from './vd.common';
import { LigneTypeEnumVd } from './vd.av';
import { ArticleBean, UniteBean } from './bean.stock';

export interface AbstractStatusTrackingBean<T> extends AbstractBean {
	statusDate: FieldMetadata<any>;
};

export interface AbstractBean {
	editable: boolean;
	action: EditActionEnumVd;
	__TYPE__: string;
	operation: ClientOperationEnumVd;
	label: string;
	visible: boolean;
	valueChanged: boolean;
};

export interface AbstractLigneBean extends AbstractBean {
	variantCode: FieldMetadata<string>;
	unite: UniteBean;
	quantite: FieldMetadata<number>;
	typeLigne: FieldMetadata<LigneTypeEnumVd>;
	article: ArticleBean;
	numero: FieldMetadata<number>;
	description: FieldMetadata<string>;
	prixTotal: FieldMetadata<any>;
	ligneId: FieldMetadata<number>;
	prixUnitaire: FieldMetadata<any>;
};