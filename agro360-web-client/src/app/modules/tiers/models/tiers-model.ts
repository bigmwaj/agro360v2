import { AbstractFormModel } from "src/app/shared/abstract-form-model";
import { FieldMetadata } from "src/app/shared/field-metadata";
import { TiersCategoryModel } from "./tiers-category-model";

export type TiersTypeEnumVd = {

}

export type TiersStatusEnumVd = {

}

export interface TiersModel extends AbstractFormModel {

    tiersCode: FieldMetadata<string>;

    tiersType: FieldMetadata<TiersTypeEnumVd>;

    status: FieldMetadata<TiersStatusEnumVd>;

    tiersName: FieldMetadata<string>;

    name: FieldMetadata<string>;

    firstName: FieldMetadata<string>;

    lastName: FieldMetadata<string>;

    title: FieldMetadata<string>;

    phone: FieldMetadata<string>;

    email: FieldMetadata<string>;

    address: FieldMetadata<string>;

    city: FieldMetadata<string>;

    country: FieldMetadata<string>;

    categoriesHierarchie: TiersCategoryModel;

}