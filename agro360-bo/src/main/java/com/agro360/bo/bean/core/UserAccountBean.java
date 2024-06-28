package com.agro360.bo.bean.core;

import java.time.LocalDateTime;
import java.util.List;

import com.agro360.bo.bean.common.AbstractStatusTrackingBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.core.UserAccountStatusEnumVd;
import com.agro360.vd.core.UserLangEnumVd;
import com.agro360.vd.core.UserRoleEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class UserAccountBean extends AbstractStatusTrackingBean<UserAccountStatusEnumVd>{

	private static final long serialVersionUID = 4953208601044344467L;

	private FieldMetadata<UserAccountStatusEnumVd> status = new FieldMetadata<>("Statut", UserAccountStatusEnumVd.getAsMap());

	private FieldMetadata<UserLangEnumVd> lang = new FieldMetadata<>("Langue",UserLangEnumVd.getAsMap());
	
	private FieldMetadata<List<UserRoleEnumVd>> roles = new FieldMetadata<>("Roles", UserRoleEnumVd.getAsMap());
	
	private FieldMetadata<String> magasin = new FieldMetadata<>("Magasin");
	
	private FieldMetadata<LocalDateTime> passwordLastChangeDate = new FieldMetadata<>("Password Last Change Date");
	
	private FieldMetadata<String> password = new FieldMetadata<>("Password");
	
	private FieldMetadata<String> passwordBis = new FieldMetadata<>("Password(Bis)");
	
	@Setter
	private PartnerBean partner = new PartnerBean();

}
