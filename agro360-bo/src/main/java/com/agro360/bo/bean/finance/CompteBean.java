package com.agro360.bo.bean.finance;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.finance.CompteTypeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CompteBean extends AbstractBean{

	private static final long serialVersionUID = 1969867939120937699L;

	private FieldMetadata<String> compteCode = new FieldMetadata<>("Compte");
	
	private FieldMetadata<String> libelle = new FieldMetadata<>("Libellé");

	private FieldMetadata<String> description = new FieldMetadata<>("Description");
	
	private FieldMetadata<CompteTypeEnumVd> type = new FieldMetadata<>("Type", CompteTypeEnumVd.getAsMap());

	@Setter
	private PartnerBean partner = new PartnerBean();


}
