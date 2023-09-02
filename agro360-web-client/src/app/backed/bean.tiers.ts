import { FieldMetadata } from './metadata';
import { AbstractBean, AbstractStatusTrackingBean } from './bean.common';
import { TiersStatusEnumVd, TiersTypeEnumVd } from './vd.tiers';

export interface TiersSearchBean extends AbstractBean {
	tiersName: FieldMetadata<string>;
	phone: FieldMetadata<string>;
	tiersCode: FieldMetadata<string>;
	email: FieldMetadata<string>;
	tiersType: FieldMetadata<TiersTypeEnumVd>;
	status: FieldMetadata<TiersStatusEnumVd>;
};

export interface TiersBean extends AbstractStatusTrackingBean<TiersStatusEnumVd> {
	tiersName: FieldMetadata<string>;
	phone: FieldMetadata<string>;
	tiersCode: FieldMetadata<string>;
	categoriesHierarchie: TiersCategoryBean;
	country: FieldMetadata<string>;
	city: FieldMetadata<string>;
	status: FieldMetadata<TiersStatusEnumVd>;
	name: FieldMetadata<string>;
	title: FieldMetadata<string>;
	firstName: FieldMetadata<string>;
	email: FieldMetadata<string>;
	address: FieldMetadata<string>;
	tiersType: FieldMetadata<TiersTypeEnumVd>;
	lastName: FieldMetadata<string>;
};

export interface CategoryBean extends AbstractBean {
	description: FieldMetadata<string>;
	children: Array<CategoryBean>;
	categoryCode: FieldMetadata<string>;
};

export interface TiersCategoryBean extends AbstractBean {
	description: FieldMetadata<string>;
	selected: FieldMetadata<boolean>;
	children: Array<TiersCategoryBean>;
	categoryCode: FieldMetadata<string>;
};