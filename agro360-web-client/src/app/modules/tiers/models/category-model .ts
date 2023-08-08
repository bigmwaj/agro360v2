import { AbstractFormModel } from "src/app/shared/abstract-form-model";
import { FieldMetadata } from "src/app/shared/field-metadata";

export interface CategoryModel extends AbstractFormModel {
    
    categoryCode: FieldMetadata<string>;

    description: FieldMetadata<string>;

    children: CategoryModel[];

}