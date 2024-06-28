import { FieldMetadata } from './metadata';
import { PartnerStatusEnumVd, PartnerTypeEnumVd, UserAccountStatusEnumVd, UserLangEnumVd } from './vd.core';
import { AbstractCategoryBean, AbstractHierarchisableBean, AbstractSearchBean, AbstractStatusTrackingBean } from './bean.common';

export interface UserAccountBean extends AbstractStatusTrackingBean<UserAccountStatusEnumVd> {
	passwordLastChangeDate: FieldMetadata<any>;
	magasin: FieldMetadata<string>;
	passwordBis: FieldMetadata<string>;
	partner: PartnerBean;
	roles: FieldMetadata<any>;
	lang: FieldMetadata<UserLangEnumVd>;
	status: FieldMetadata<UserAccountStatusEnumVd>;
	password: FieldMetadata<string>;
};

export interface PartnerSearchBean extends AbstractSearchBean {
	phone: FieldMetadata<string>;
	city: FieldMetadata<string>;
	partnerCode: FieldMetadata<string>;
	categoryBtn: FieldMetadata<string>;
	email: FieldMetadata<string>;
	createPartnerBtn: FieldMetadata<string>;
	statusIn: FieldMetadata<PartnerStatusEnumVd>;
	type: FieldMetadata<PartnerTypeEnumVd>;
};

export interface PartnerBean extends AbstractStatusTrackingBean<PartnerStatusEnumVd> {
	phone: FieldMetadata<string>;
	city: FieldMetadata<string>;
	updatePasswordBtn: FieldMetadata<string>;
	categoriesHierarchie: PartnerCategoryBean;
	partnerCode: FieldMetadata<string>;
	lastName: FieldMetadata<string>;
	address: FieldMetadata<string>;
	status: FieldMetadata<PartnerStatusEnumVd>;
	createAccountBtn: FieldMetadata<string>;
	country: FieldMetadata<string>;
	deactivateAccountBtn: FieldMetadata<string>;
	updateAccountBtn: FieldMetadata<string>;
	activateAccountBtn: FieldMetadata<string>;
	firstName: FieldMetadata<string>;
	email: FieldMetadata<string>;
	title: FieldMetadata<string>;
	type: FieldMetadata<PartnerTypeEnumVd>;
	partnerName: FieldMetadata<string>;
	name: FieldMetadata<string>;
};

export interface PartnerCategoryBean extends AbstractHierarchisableBean<CategoryBean> {

};

export interface CategoryBean extends AbstractCategoryBean<CategoryBean> {

};