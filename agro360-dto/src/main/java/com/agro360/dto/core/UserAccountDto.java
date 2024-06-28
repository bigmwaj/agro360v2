package com.agro360.dto.core;

import java.time.LocalDateTime;

import com.agro360.dto.common.AbstractStatusTrackingDto;
import com.agro360.vd.core.UserAccountStatusEnumVd;
import com.agro360.vd.core.UserLangEnumVd;

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

	@Id
	@Column(name = "PARTNER_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private String partnerCode;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private UserAccountStatusEnumVd status;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "PASSWORD_LAST_CHANGE_DATE")
	private LocalDateTime passwordLastChangeDate;

	@Column(name = "LANG")
	@Enumerated(EnumType.STRING)
	private UserLangEnumVd lang;

	@Column(name = "ROLES")
	private String roles;

	@Column(name = "MAGASIN")
	private String magasin;

}
