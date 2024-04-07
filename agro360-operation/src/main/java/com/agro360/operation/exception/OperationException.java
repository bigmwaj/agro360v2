package com.agro360.operation.exception;

import com.agro360.bo.message.Message;

public class OperationException extends RuntimeException{

	private static final long serialVersionUID = -477548825168844940L;
	
	private Message message;
	
	public OperationException(Message message) {
		super(message.getMessage());
		this.message = message;
	}
	
	public Message getLogicMessage() {
		return message;
	}

}
