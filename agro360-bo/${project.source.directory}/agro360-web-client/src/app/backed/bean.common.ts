import { FieldMetadata } from './metadata';
import { ClientOperationEnumVd } from './vd.common';
import { LigneTypeEnumVd } from './vd.av';
import { ArticleBean, UniteBean } from './bean.stock';

export interface AbstractLigneBean<E> extends AbstractBean {
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

export interface AbstractBean {
	editable: boolean;
	deleteBtn: FieldMetadata<string>;
	__TYPE__: string;
	label: string;
	rootBean: AbstractBean;
	visible: boolean;
	ownerBean: AbstractBean;
	valueChanged: boolean;
	saveBtn: FieldMetadata<string>;
	action: ClientOperationEnumVd;
};

export interface AbstractSearchBean extends AbstractBean {
	createBtn: FieldMetadata<string>;
	pageIndex: number;
	pageSize: number;
	pageSizeOptions: any;
	length: number;
};

export interface AbstractStatusTrackingBean<T> extends AbstractBean {
	changeStatusBtn: FieldMetadata<string>;
	statusDate: FieldMetadata<any>;
};