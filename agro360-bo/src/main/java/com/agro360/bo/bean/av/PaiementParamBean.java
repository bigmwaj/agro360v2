package com.agro360.bo.bean.av;

import java.math.BigDecimal;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.bean.finance.CompteBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.av.PaiementInteracViaEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class PaiementParamBean extends AbstractBean {

	private static final long serialVersionUID = 8594918631594333065L;
	
	public PaiementParamBean() {
		
	}
	
	public PaiementParamBean(CompteBean compte) {
		this.compte = compte;
	}

	private FieldMetadata<BigDecimal> montant = new FieldMetadata<>("Montant");
	
	private FieldMetadata<String> email = new FieldMetadata<>("Email");
	
	private FieldMetadata<String> phone = new FieldMetadata<>("Phone");
	
	private FieldMetadata<PaiementInteracViaEnumVd> interacVia = new FieldMetadata<>("Interac via", PaiementInteracViaEnumVd.getAsMap());

	@Setter
	private CompteBean compte = new CompteBean();
}
