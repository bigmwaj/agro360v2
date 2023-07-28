package com.agro360.service.bean.tiers;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.tiers.TiersStatusEnumVd;
import com.agro360.vd.tiers.TiersTypeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class TiersSearchBean extends AbstractBean{

	private static final long serialVersionUID = 355487810396906725L;

	private FieldMetadata<String> tiersCode = new FieldMetadata<>();

	private FieldMetadata<TiersTypeEnumVd> tiersType = new FieldMetadata<>();

	private FieldMetadata<TiersStatusEnumVd> status = new FieldMetadata<>();

	private FieldMetadata<String> tiersName = new FieldMetadata<>();

	private FieldMetadata<String> phone = new FieldMetadata<>();

	private FieldMetadata<String> email = new FieldMetadata<>();
	
}
