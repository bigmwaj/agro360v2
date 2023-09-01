import { Message } from './message';

export interface FieldMetadata<T> {	
	readonly __TYPE__: string;
	editable: boolean;
	valueOptions: any;
	value: T;
	valueI18n: string;
	label: string;
	required: boolean;
	min: number;
	tooltip: string;
	max: number;
	messages: Array<Message>;
	maxLength: number;
	valueChanged: boolean;
	icon: string;
};