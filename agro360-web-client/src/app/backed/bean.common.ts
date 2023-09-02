import { FieldMetadata } from './metadata';
import { EditActionEnumVd } from './vd.common';
import { TypeLigneEnumVd } from './vd.stock';
import { ArticleBean, UniteBean } from './bean.stock';

export interface AbstractLigneBean extends AbstractBean {
	unite: UniteBean;
	typeLigne: FieldMetadata<TypeLigneEnumVd>;
	description: FieldMetadata<string>;
	prixTotal: FieldMetadata<number>;
	ligneId: FieldMetadata<number>;
	quantite: FieldMetadata<number>;
	prixUnitaire: FieldMetadata<number>;
	article: ArticleBean;
	variantCode: FieldMetadata<string>;
	numero: FieldMetadata<number>;
};

export interface AbstractBean {
	action: EditActionEnumVd;
	__TYPE__: string;
	valueChanged: boolean;
};

export interface AbstractStatusTrackingBean<T> extends AbstractBean {
	statusDate: FieldMetadata<any>;
};