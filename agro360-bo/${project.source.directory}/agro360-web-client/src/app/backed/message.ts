import { MessageTypeEnumVd } from './vd.common';

export interface Message {
	type: MessageTypeEnumVd;
	message: string;
};