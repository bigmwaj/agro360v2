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
public class FactureTaxeBean extends AbstractBean {

	private static final long serialVersionUID = -7019145572813595977L;

	private FieldMetadata<BigDecimal> montant = new FieldMetadata<>("Montant");

	private FieldMetadata<Double> taux = new FieldMetadata<>("Taux");

	@Setter
	private TaxeBean taxe = new TaxeBean();
}
