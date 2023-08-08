import { AbstractFormModel } from "src/app/shared/abstract-form-model";
import { FieldMetadata } from "src/app/shared/field-metadata";

export interface TiersCategoryModel extends AbstractFormModel {

    categoryCode: FieldMetadata<string>;

    description: FieldMetadata<string>;

    selected: FieldMetadata<boolean>;

    children: TiersCategoryModel[];

}