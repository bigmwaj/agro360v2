import { Message } from './message';

export interface FieldMetadata<T> {
	editable: boolean;
	__TYPE__: string;
	messages: Array<Message>;
	tooltip: string;
	visible: boolean;
	max: number;
	value: T;
	valueOptions: any;
	label: string;
	required: boolean;
	min: number;
	valueI18n: string;
	maxLength: number;
	valueChanged: boolean;
	icon: string;
};