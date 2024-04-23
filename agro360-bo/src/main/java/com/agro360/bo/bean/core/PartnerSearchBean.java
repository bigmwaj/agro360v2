package com.agro360.bo.bean.core;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.core.PartnerStatusEnumVd;
import com.agro360.vd.core.PartnerTypeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class PartnerSearchBean extends AbstractBean{

	private static final long serialVersionUID = 355487810396906725L;

	private FieldMetadata<String> partnerCode = new FieldMetadata<>("Code");

	private FieldMetadata<PartnerTypeEnumVd> type = new FieldMetadata<>("Type");

	private FieldMetadata<PartnerStatusEnumVd> status = new FieldMetadata<>("Statut");

	private FieldMetadata<String> partnerName = new FieldMetadata<>("Nom du Partner");

	private FieldMetadata<String> phone = new FieldMetadata<>("Téléphone");

	private FieldMetadata<String> email = new FieldMetadata<>("Email");

}
