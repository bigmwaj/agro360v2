package com.agro360.bo.bean.core;

import com.agro360.bo.bean.common.AbstractStatusTrackingBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.core.UserAccountStatusEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class UserAccountBean extends AbstractStatusTrackingBean<UserAccountStatusEnumVd>{

	private static final long serialVersionUID = 4953208601044344467L;

	private FieldMetadata<String> partnerCode = new FieldMetadata<>("Partner");

	private FieldMetadata<UserAccountStatusEnumVd> status = new FieldMetadata<>("Statut", UserAccountStatusEnumVd.getAsMap());

	private FieldMetadata<String> password = new FieldMetadata<>("Password");

}
