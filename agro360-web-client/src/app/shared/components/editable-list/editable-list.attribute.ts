export type InputType = 'text' | 'number' | 'date' | 'time' | 'select' | 'checkbox'

export declare interface EditableListAttribute{
    name:string;

    label:string;

    updatable:boolean;

    type?:InputType;

    required?:boolean;

    defaultValue?: boolean | string | number;
    
}