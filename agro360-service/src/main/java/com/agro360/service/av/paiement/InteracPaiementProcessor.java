package com.agro360.service.av.paiement;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.av.PaiementParamBean;
import com.agro360.operation.context.ClientContext;

@Component
public class InteracPaiementProcessor {
	
	public void processPaiement(ClientContext ctx, PaiementParamBean param) {
		var apiKey = "";
		var amount = 0.0;
		var via = param.getInteracVia().getValue();
		switch (via) {
		case EMAIL:
			var clientEmail = "";
			processPaiementViaEmail(apiKey, clientEmail, amount);
			
			break;
		case PHONE:
			var clientPhone = "";
			processPaiementViaPhone(apiKey, clientPhone, amount);
			
			break;

		default:
			break;
		}
		
	}
	
	private void processPaiementViaEmail(String apiKey, String clientPhone, Double amount) {
		
	}
	
	private void processPaiementViaPhone(String apiKey, String clientPhone, Double amount) {
		
	}
}
