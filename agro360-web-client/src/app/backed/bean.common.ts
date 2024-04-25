import { FieldMetadata } from './metadata';
import { ClientOperationEnumVd } from './vd.common';
import { LigneTypeEnumVd } from './vd.av';
import { ArticleBean, UniteBean } from './bean.stock';

export interface AbstractLigneBean extends AbstractBean {
	variantCode: FieldMetadata<string>;
	unite: UniteBean;
	quantite: FieldMetadata<number>;
	prixUnitaire: FieldMetadata<number>;
	typeLigne: FieldMetadata<LigneTypeEnumVd>;
	article: ArticleBean;
	numero: FieldMetadata<number>;
	description: FieldMetadata<string>;
	ligneId: FieldMetadata<number>;
	prixTotal: FieldMetadata<number>;
};

export interface AbstractStatusTrackingBean<T> extends AbstractBean {
	statusDate: FieldMetadata<any>;
};

export interface AbstractBean {
	editable: boolean;
	__TYPE__: string;
	label: string;
	rootBean: AbstractBean;
	visible: boolean;
	ownerBean: AbstractBean;
	valueChanged: boolean;
	action: ClientOperationEnumVd;
};