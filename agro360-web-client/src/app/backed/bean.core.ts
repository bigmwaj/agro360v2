import { FieldMetadata } from './metadata';
import { PartnerStatusEnumVd, PartnerTypeEnumVd, UserAccountStatusEnumVd } from './vd.core';
import { AbstractBean, AbstractStatusTrackingBean } from './bean.common';

export interface CategoryBean extends AbstractBean {
	categoryCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
	children: Array<CategoryBean>;
	parentCategoryCode: FieldMetadata<string>;
};

export interface PartnerSearchBean extends AbstractBean {
	phone: FieldMetadata<string>;
	partnerCode: FieldMetadata<string>;
	email: FieldMetadata<string>;
	status: FieldMetadata<PartnerStatusEnumVd>;
	type: FieldMetadata<PartnerTypeEnumVd>;
	partnerName: FieldMetadata<string>;
};

export interface PartnerCategoryBean extends AbstractBean {
	children: Array<PartnerCategoryBean>;
	categoryCode: FieldMetadata<string>;
	description: FieldMetadata<string>;
	selected: FieldMetadata<boolean>;
};

export interface PartnerBean extends AbstractStatusTrackingBean<PartnerStatusEnumVd> {
	phone: FieldMetadata<string>;
	city: FieldMetadata<string>;
	categoriesHierarchie: PartnerCategoryBean;
	partnerCode: FieldMetadata<string>;
	lastName: FieldMetadata<string>;
	address: FieldMetadata<string>;
	status: FieldMetadata<PartnerStatusEnumVd>;
	country: FieldMetadata<string>;
	firstName: FieldMetadata<string>;
	email: FieldMetadata<string>;
	title: FieldMetadata<string>;
	type: FieldMetadata<PartnerTypeEnumVd>;
	partnerName: FieldMetadata<string>;
	name: FieldMetadata<string>;
};

export interface UserAccountBean extends AbstractStatusTrackingBean<UserAccountStatusEnumVd> {
	partnerCode: FieldMetadata<string>;
	status: FieldMetadata<UserAccountStatusEnumVd>;
	password: FieldMetadata<string>;
};