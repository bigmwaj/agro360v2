package com.agro360.dto.core;

import com.agro360.dto.common.AbstractStatusTrackingDto;
import com.agro360.vd.core.UserAccountStatusEnumVd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "CORE_TBL_USER_ACCOUNT")
public class UserAccountDto extends AbstractStatusTrackingDto<UserAccountStatusEnumVd> {
	
	public static final int PARTNER_CODE_LENGTH = 16;

	@Id
	@Column(name = "PARTNER_CODE", updatable = false, length = PARTNER_CODE_LENGTH)
	@EqualsAndHashCode.Include()
	private String partnerCode;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, length = UserAccountStatusEnumVd.COLUMN_LENGTH)
	private UserAccountStatusEnumVd status;

	@Column(name = "PASSWORD", length = 255)
	private String password;
	
}
