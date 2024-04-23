import { AbstractBean } from 'src/app/backed/bean.common';
import { FieldMetadata } from '../backed/metadata';
import { ClientOperationEnumVd } from '../backed/vd.common';

export class BeanTools{

    private static isArray(field: any): boolean {
        return Array.isArray(field)
    }

    private static isFieldMetadata(field: any): boolean {        
        return Object.keys(field).includes('__TYPE__') && field['__TYPE__'] == 'FIELD_METADATA'
    }

    private static isFormBean(field: any): boolean {
        return Object.keys(field).includes('__TYPE__') && field['__TYPE__'] == 'BEAN'
    }

    private static setValueChagedStatus(bean: AbstractBean): boolean {
        const changed = Object.keys(bean).map(k => {
            type ObjectKey = keyof typeof bean;
            const key = k as ObjectKey;
            const field = bean[key];
            if( field == null ){
                return false;
            } else if (this.isArray(field)) {
                const arr = <Array<any>><unknown>field;
                return arr.map(e => this.setValueChagedStatus(e))
                    .reduce((a, b) => a || b, false);
            } else if (this.isFieldMetadata(field)) {                
                const fmd = <FieldMetadata<any>><unknown>field;
                return fmd.valueChanged;
            } else if (this.isFormBean(field)) {
                const fb = <AbstractBean><unknown>field;
                return this.setValueChagedStatus(fb)
            } else {
                return false
            }
        }).reduce((a, b) => a || b, false);

        bean.valueChanged = changed || bean.valueChanged;
        if (bean.valueChanged && bean.action != ClientOperationEnumVd.CREATE && bean.action != ClientOperationEnumVd.DELETE) {
            bean.action = ClientOperationEnumVd.UPDATE;
        }

        return bean.valueChanged;
    }

    public static reviewBeanAction<T extends AbstractBean>(bean: T): T {
        this.setValueChagedStatus(bean);
        return bean;
    }

    public static reviewBeansAction<T extends AbstractBean>(beans: Array<T>): Array<T> {
        return beans.map(e => this.reviewBeanAction(e));
    }

}
