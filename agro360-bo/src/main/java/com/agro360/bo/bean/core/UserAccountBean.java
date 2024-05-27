package com.agro360.bo.bean.core;

import java.time.LocalDateTime;

import com.agro360.bo.bean.common.AbstractStatusTrackingBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.core.UserAccountStatusEnumVd;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class UserAccountBean extends AbstractStatusTrackingBean<UserAccountStatusEnumVd>{

	private static final long serialVersionUID = 4953208601044344467L;

	private FieldMetadata<UserAccountStatusEnumVd> status = new FieldMetadata<>("Statut", UserAccountStatusEnumVd.getAsMap());

	private FieldMetadata<String> lang = new FieldMetadata<>("Langue");
	
	private FieldMetadata<String> roles = new FieldMetadata<>("Roles");
	
	private FieldMetadata<String> magasin = new FieldMetadata<>("Magasin");
	
	private FieldMetadata<LocalDateTime> passwordLastChangeDate = new FieldMetadata<>("Password Last Change Date");
	
	@JsonIgnore
	private FieldMetadata<String> password = new FieldMetadata<>("Password");
	
	@Setter
	private PartnerBean partner = new PartnerBean();

}
