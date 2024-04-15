package com.agro360.bo.bean.finance;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.finance.TransactionStatusEnumVd;
import com.agro360.vd.finance.TransactionTypeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class TransactionSearchBean extends AbstractBean{

	private static final long serialVersionUID = -1273209527092665188L;

	private FieldMetadata<String> transactionCode = new FieldMetadata<>("Transaction");

	private FieldMetadata<TransactionTypeEnumVd> type = new FieldMetadata<>("Type", TransactionTypeEnumVd.getAsMap());

	private FieldMetadata<TransactionStatusEnumVd> status = new FieldMetadata<>("Statut", TransactionStatusEnumVd.getAsMap());

	private FieldMetadata<String> partner = new FieldMetadata<>("Partner");

	private FieldMetadata<String> rubrique = new FieldMetadata<>("Rubrique");

	private FieldMetadata<String> compte = new FieldMetadata<>("Compte");
	
}
