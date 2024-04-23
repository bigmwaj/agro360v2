package com.agro360.bo.message;

import java.util.List;

import com.agro360.vd.common.MessageTypeEnumVd;

import lombok.Data;

@Data
public class Message {

	private MessageTypeEnumVd type;

	private String message;

	private Message(MessageTypeEnumVd type, String message) {
		this.message = message;
		this.type = type;
	}

	public static boolean hasAnyError(List<Message> messages) {
		return messages.stream().map(Message::getType).anyMatch(MessageTypeEnumVd.ERROR::equals);
	}

	public static final Message info(String message) {
		return new Message(MessageTypeEnumVd.INFO, message);
	}

	public static final Message success(String message) {
		return new Message(MessageTypeEnumVd.SUCCESS, message);
	}

	public static final Message error(String message) {
		return new Message(MessageTypeEnumVd.ERROR, message);
	}

	public static final Message warn(String message) {
		return new Message(MessageTypeEnumVd.WARNING, message);
	}
}
