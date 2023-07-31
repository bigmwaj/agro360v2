package com.agro360.web.bean.tiers;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class TiersDataTableHeaderBean extends AbstractBean {

	private static final long serialVersionUID = 3859251324867804601L;

	private FieldMetadata<String> tiersCode = new FieldMetadata<>();

	private FieldMetadata<String> tiersType = new FieldMetadata<>();

	private FieldMetadata<String> status = new FieldMetadata<>();

	private FieldMetadata<String> tiersName = new FieldMetadata<>();

	private FieldMetadata<String> phone = new FieldMetadata<>();

	private FieldMetadata<String> email = new FieldMetadata<>();

	private FieldMetadata<String> address = new FieldMetadata<>();

	private FieldMetadata<String> city = new FieldMetadata<>();

	private FieldMetadata<String> country = new FieldMetadata<>();

}
