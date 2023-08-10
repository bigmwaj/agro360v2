import { Message } from './message';

export interface FieldMetadata<T> {
	editable: boolean;
	valueOptions: any;
	value: T;
	required: boolean;
	min: number;
	tooltip: string;
	max: number;
	messages: Array<Message>;
	maxLength: number;
	icon: string;
};