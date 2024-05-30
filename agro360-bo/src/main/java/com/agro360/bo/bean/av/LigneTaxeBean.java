package com.agro360.bo.bean.av;

import java.math.BigDecimal;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.bean.finance.TaxeBean;
import com.agro360.bo.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class LigneTaxeBean extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<Long> ligneId = new FieldMetadata<>("Ligne ID");
	
	private FieldMetadata<String> commandeCode = new FieldMetadata<>("Commande");
	
	private FieldMetadata<BigDecimal> montant = new FieldMetadata<>("Montant");

	private FieldMetadata<Double> taux = new FieldMetadata<>("Taux");

	@Setter
	private TaxeBean taxe = new TaxeBean();
}
