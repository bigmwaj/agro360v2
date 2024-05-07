package com.agro360.bo.bean.av;

import java.math.BigDecimal;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.bean.finance.CompteBean;
import com.agro360.bo.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class PaiementBean extends AbstractBean {

	private static final long serialVersionUID = 8594918631594333065L;
	
	public PaiementBean() {
		
	}
	
	public PaiementBean(CompteBean compte) {
		this.compte = compte;
	}

	private FieldMetadata<String> ref = new FieldMetadata<>("Ref");
	
	private FieldMetadata<BigDecimal> reste = new FieldMetadata<>("Reste");
	
	private FieldMetadata<BigDecimal> montant = new FieldMetadata<>("Montant");

	private FieldMetadata<String> encaissementBtn = new FieldMetadata<>();

	@Setter
	private CompteBean compte = new CompteBean();
}
