package com.agro360.bo.bean.core;

import com.agro360.bo.bean.common.AbstractSearchBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.core.PartnerStatusEnumVd;
import com.agro360.vd.core.PartnerTypeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class PartnerSearchBean extends AbstractSearchBean{

	private static final long serialVersionUID = 355487810396906725L;

	private FieldMetadata<String> partnerCode = new FieldMetadata<>("Partenaire");

	private FieldMetadata<PartnerTypeEnumVd> type = new FieldMetadata<>("Type", PartnerTypeEnumVd.getAsMap());

	private FieldMetadata<PartnerStatusEnumVd> statusIn = new FieldMetadata<>("Statut", PartnerStatusEnumVd.getAsMap());

	private FieldMetadata<String> phone = new FieldMetadata<>("Téléphone");

	private FieldMetadata<String> email = new FieldMetadata<>("Email");
	
	private FieldMetadata<String> city = new FieldMetadata<>("Ville");
	
	private FieldMetadata<String> createPartnerBtn = new FieldMetadata<>();
	
	private FieldMetadata<String> categoryBtn = new FieldMetadata<>();

}
