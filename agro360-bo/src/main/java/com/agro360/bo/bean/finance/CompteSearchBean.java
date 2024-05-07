package com.agro360.bo.bean.finance;

import com.agro360.bo.bean.common.AbstractSearchBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.finance.CompteTypeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CompteSearchBean extends AbstractSearchBean{

	private static final long serialVersionUID = 355487810396906725L;

	private FieldMetadata<String> compte = new FieldMetadata<>("Compte");
	
	private FieldMetadata<CompteTypeEnumVd> type = new FieldMetadata<>("Type", CompteTypeEnumVd.getAsMap());
	
	private FieldMetadata<String> partner = new FieldMetadata<>("Partenaire");

}
