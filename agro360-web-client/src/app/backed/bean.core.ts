import { FieldMetadata } from './metadata';
import { PartnerStatusEnumVd, PartnerTypeEnumVd, UserAccountStatusEnumVd } from './vd.core';
import { AbstractBean, AbstractSearchBean, AbstractStatusTrackingBean } from './bean.common';

export interface PartnerCategoryBean extends AbstractBean {
	categoryCode: FieldMetadata<any>;
	children: Array<PartnerCategoryBean>;
	description: FieldMetadata<any>;
	selected: FieldMetadata<any>;
};

export interface PartnerSearchBean extends AbstractSearchBean {
	email: FieldMetadata<any>;
	city: FieldMetadata<any>;
	categoryBtn: FieldMetadata<any>;
	createPartnerBtn: FieldMetadata<any>;
	statusIn: FieldMetadata<PartnerStatusEnumVd>;
	type: FieldMetadata<PartnerTypeEnumVd>;
	partnerCode: FieldMetadata<any>;
	phone: FieldMetadata<any>;
};

export interface UserAccountBean extends AbstractStatusTrackingBean<UserAccountStatusEnumVd> {
	passwordLastChangeDate: FieldMetadata<any>;
	partner: PartnerBean;
	roles: FieldMetadata<any>;
	magasin: FieldMetadata<any>;
	password: FieldMetadata<any>;
	lang: FieldMetadata<any>;
	status: FieldMetadata<UserAccountStatusEnumVd>;
};

export interface CategoryBean extends AbstractBean {
	categoryCode: FieldMetadata<any>;
	children: Array<CategoryBean>;
	parentCategoryCode: FieldMetadata<any>;
	description: FieldMetadata<any>;
};

export interface PartnerBean extends AbstractStatusTrackingBean<PartnerStatusEnumVd> {
	email: FieldMetadata<any>;
	firstName: FieldMetadata<any>;
	categoriesHierarchie: PartnerCategoryBean;
	status: FieldMetadata<PartnerStatusEnumVd>;
	country: FieldMetadata<any>;
	partnerCode: FieldMetadata<any>;
	address: FieldMetadata<any>;
	city: FieldMetadata<any>;
	partnerName: FieldMetadata<any>;
	lastName: FieldMetadata<any>;
	name: FieldMetadata<any>;
	title: FieldMetadata<any>;
	type: FieldMetadata<PartnerTypeEnumVd>;
	phone: FieldMetadata<any>;
};