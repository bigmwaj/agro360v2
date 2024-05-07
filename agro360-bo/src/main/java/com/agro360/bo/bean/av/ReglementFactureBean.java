package com.agro360.bo.bean.av;

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
public class ReglementFactureBean extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<Long> reglementId = new FieldMetadata<>("ID");

	private FieldMetadata<String> factureCode = new FieldMetadata<>("Facture");
	
	@Setter
	private TransactionBean transaction = new TransactionBean();
	
	public ReglementFactureBean() {
	}
	
	public ReglementFactureBean(TransactionBean transaction, String factureCode) {
		this.transaction = transaction;
		this.factureCode.setValue(factureCode);
	}
	
	
}
