package com.agro360.service.utils;

import lombok.Data;

@Data
public class Message {

	public enum TYPE {
		SUCCESS, ERROR
	}

	private Message(TYPE type, String message) {
		this.message = message;
		this.type = type;
	}
	
	public static final Message success(String message) {
		return new Message(TYPE.SUCCESS, message);
	}
	
	public static final Message error(String message) {
		return new Message(TYPE.ERROR, message);
	}

	private TYPE type;

	private String message;
}
