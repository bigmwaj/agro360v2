package com.agro360.bo.bean.finance;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class TransfertBean extends AbstractBean{

	private static final long serialVersionUID = -8728011191825677594L;

	private FieldMetadata<LocalDate> date = new FieldMetadata<>("Date Transaction");
	
	private FieldMetadata<String> note = new FieldMetadata<>("Note");
	
	private FieldMetadata<BigDecimal> montant = new FieldMetadata<>("Montant");
	
	@Setter
	private PartnerBean partner = new PartnerBean();
	
	@Setter
	private CompteBean compteSource = new CompteBean();
	
	@Setter
	private CompteBean compteCible = new CompteBean();
	
}
