package com.agro360.bo.bean.av;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.bean.finance.TransactionBean;
import com.agro360.bo.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ReglementCommandeBean extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<Long> reglementId = new FieldMetadata<>("ID");

	private FieldMetadata<String> commandeCode = new FieldMetadata<>("Commande");

	@Setter
	private TransactionBean transaction = new TransactionBean();
}
