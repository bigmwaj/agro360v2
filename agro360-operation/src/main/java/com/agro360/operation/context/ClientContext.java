package com.agro360.operation.context;

import java.util.ArrayList;
import java.util.List;

import com.agro360.bo.message.Message;

import lombok.Getter;

public class ClientContext {

	@Getter
	private List<Message> messages = new ArrayList<>();
	
	public final Message info(String message) {
		var msg = Message.info(message);
		messages.add(msg);
		
		return msg;
	}
	
	public final Message success(String message) {
		var msg = Message.success(message);
		messages.add(msg);
		
		return msg;
	}
	
	public final Message error(String message) {
		var msg = Message.error(message);
		messages.add(msg);
		
		return msg;
	}
	
	public final Message warn(String message) {
		var msg = Message.warn(message);
		messages.add(msg);
		
		return msg;
	}
}
