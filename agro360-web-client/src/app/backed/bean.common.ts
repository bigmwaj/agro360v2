import { FieldMetadata } from './metadata';
import { EditActionEnumVd } from './vd.common';
import { TypeLigneEnumVd } from './vd.stock';
import { ArticleBean, UniteBean } from './bean.stock';

export interface AbstractBean {

};

export interface AbstractFormBean extends AbstractBean {
	action: EditActionEnumVd;
};

export interface AbstractLigneBean extends AbstractFormBean {
	unite: UniteBean;
	typeLigne: FieldMetadata<TypeLigneEnumVd>;
	description: FieldMetadata<string>;
	ligneId: FieldMetadata<number>;
	quantite: FieldMetadata<number>;
	prixUnitaire: FieldMetadata<number>;
	article: ArticleBean;
	variantCode: FieldMetadata<string>;
	numero: FieldMetadata<number>;
};