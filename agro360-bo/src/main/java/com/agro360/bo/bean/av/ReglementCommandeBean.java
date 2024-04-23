package com.agro360.bo.bean.av;

import java.math.BigDecimal;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.bean.finance.TransactionBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.bo.utils.TypeScriptInfos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = false)
@TypeScriptInfos(igroreSuperClassParam = true)
public class ReglementCommandeBean extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<Long> reglementId = new FieldMetadata<>("ID");

	private FieldMetadata<String> commandeCode = new FieldMetadata<>("Commande");

	private FieldMetadata<BigDecimal> montant = new FieldMetadata<>("Montant");

	@Setter
	private TransactionBean transaction = new TransactionBean();
}
