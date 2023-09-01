package com.agro360.service.exception;

import com.agro360.service.message.Message;

public class ServiceLogicException extends RuntimeException{

	private static final long serialVersionUID = -477548825168844940L;
	
	private Message message;
	
	public ServiceLogicException(Message message) {
		this.message = message;
	}
	
	public Message getLogicMessage() {
		return message;
	}

}
