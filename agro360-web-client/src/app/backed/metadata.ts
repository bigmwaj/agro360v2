import { Message } from './message';

export interface FieldMetadata<T> {
	editable: boolean;
	__TYPE__: string;
	tooltip: string;
	max: number;
	messages: Array<Message>;
	value: T;
	valueOptions: any;
	label: string;
	required: boolean;
	visible: boolean;
	min: number;
	valueI18n: string;
	maxLength: number;
	valueChanged: boolean;
	icon: string;
};