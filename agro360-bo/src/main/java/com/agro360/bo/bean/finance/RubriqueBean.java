package com.agro360.bo.bean.finance;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.finance.TransactionTypeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class RubriqueBean extends AbstractBean{

	private static final long serialVersionUID = -7531837405694818521L;

	private FieldMetadata<String> rubriqueCode = new FieldMetadata<>("Rubrique");

	private FieldMetadata<TransactionTypeEnumVd> type = new FieldMetadata<>("Type", TransactionTypeEnumVd.getAsMap());

	private FieldMetadata<String> libelle = new FieldMetadata<>("Nom");

	private FieldMetadata<String> description = new FieldMetadata<>("Description");


}
