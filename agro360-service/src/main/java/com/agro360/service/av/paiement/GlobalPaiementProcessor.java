package com.agro360.service.av.paiement;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.av.PaiementParamBean;
import com.agro360.operation.context.ClientContext;

@Component
public class GlobalPaiementProcessor {
	public void processPaiement(ClientContext ctx, PaiementParamBean param) {
		var apiKey = "";
		var clientPhone = "";
		var amount = 0.0;
		
		processPaiement(apiKey, clientPhone, amount);
		
	}
	
	private void processPaiement(String apiKey, String clientPhone, Double amount) {
		
	}
}
