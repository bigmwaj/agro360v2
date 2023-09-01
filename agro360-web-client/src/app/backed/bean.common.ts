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

export interface AbstractStatusTrackingFormBean<T> extends AbstractBean {
	statusDate: FieldMetadata<any>;
};

export interface AbstractBean {
	action: EditActionEnumVd;
	valueChanged: boolean;
	readonly __TYPE__: string;
};