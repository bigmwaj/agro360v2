package com.agro360.bo.bean.finance;

import com.agro360.bo.bean.common.AbstractSearchBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.finance.TransactionTypeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class RubriqueSearchBean extends AbstractSearchBean{

	private static final long serialVersionUID = 355487810396906725L;

	private FieldMetadata<String> rubrique = new FieldMetadata<>("Rubrique");

	private FieldMetadata<TransactionTypeEnumVd> type = new FieldMetadata<>("Type", TransactionTypeEnumVd.getAsMap());

}
