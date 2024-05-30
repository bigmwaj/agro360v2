import { FieldMetadata } from './metadata';
import { ClientOperationEnumVd } from './vd.common';
import { LigneTypeEnumVd } from './vd.av';
import { ArticleBean, UniteBean } from './bean.stock';

export interface AbstractSearchBean extends AbstractBean {
	pageIndex: number;
	createBtn: FieldMetadata<any>;
	pageSize: number;
	pageSizeOptions: any;
	length: number;
};

export interface AbstractStatusTrackingBean<T> extends AbstractBean {
	changeStatusBtn: FieldMetadata<any>;
	statusDate: FieldMetadata<any>;
};

export interface AbstractLigneBean<E> extends AbstractBean {
	unite: UniteBean;
	typeLigne: FieldMetadata<LigneTypeEnumVd>;
	article: ArticleBean;
	variantCode: FieldMetadata<any>;
	prixTotal: FieldMetadata<any>;
	quantite: FieldMetadata<any>;
	numero: FieldMetadata<any>;
	description: FieldMetadata<any>;
	prixUnitaire: FieldMetadata<any>;
	ligneId: FieldMetadata<any>;
};

export interface AbstractBean {
	editable: boolean;
	__TYPE__: string;
	saveBtn: FieldMetadata<any>;
	label: string;
	deleteBtn: FieldMetadata<any>;
	rootBean: AbstractBean;
	visible: boolean;
	ownerBean: AbstractBean;
	valueChanged: boolean;
	action: ClientOperationEnumVd;
};