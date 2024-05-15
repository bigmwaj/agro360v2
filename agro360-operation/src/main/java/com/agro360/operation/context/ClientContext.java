package com.agro360.operation.context;

import java.util.ArrayList;
import java.util.List;

import com.agro360.bo.bean.core.UserAccountBean;
import com.agro360.bo.message.Message;

import lombok.Getter;

public class ClientContext {

	private final UserAccountBean userInfo;
	
	public ClientContext(UserAccountBean userInfo) {
		this.userInfo = userInfo;
	}
	
	@Getter
	private List<Message> messages = new ArrayList<>();
	
	public String getCurrentUser() {
		if( userInfo == null ) {
			throw new RuntimeException("Utilisateur en cours non paramétré!");
		}
		return userInfo.getPartner().getPartnerCode().getValue();
		
	}
	
	public String getDefaultMagasin() {
		if( userInfo == null ) {
			throw new RuntimeException("Utilisateur en cours non paramétré!");
		}
		return userInfo.getMagasin().getValue();
		
	}
	
	public String getDefaultPartner() {
		return "DIVERS";
		
	}
	
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
