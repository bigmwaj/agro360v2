package com.agro360.dto.core;

import com.agro360.dto.common.AbstractStatusTrackingDto;
import com.agro360.vd.core.PartnerStatusEnumVd;
import com.agro360.vd.core.PartnerTypeEnumVd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "CORE_TBL_PARTNER")
public class PartnerDto extends AbstractStatusTrackingDto<PartnerStatusEnumVd> {
	
	public static final int PARTNER_CODE_LENGTH = 16;

	@Id
	@Column(name = "PARTNER_CODE", updatable = false, length = PARTNER_CODE_LENGTH)
	@EqualsAndHashCode.Include()
	private String partnerCode;

	@Enumerated(EnumType.STRING)
	@Column(name = "PARTNER_TYPE", updatable = false, length = 8)
	private PartnerTypeEnumVd type;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, length = PartnerStatusEnumVd.COLUMN_LENGTH)
	private PartnerStatusEnumVd status;

	@Column(name = "NAME", length = 32)
	private String name; // Company name

	@Column(name = "FIRST_NAME", length = 32)
	private String firstName;

	@Column(name = "LAST_NAME", length = 32)
	private String lastName;

	@Column(name = "TITLE", length = 8)
	private String title;

	@Column(name = "PHONE", nullable = false, length = 16)
	private String phone;

	@Column(name = "EMAIL", nullable = false, length = 32)
	private String email;

	@Column(name = "ADDRESS", length = 64)
	private String address;

	@Column(name = "CITY", length = 16)
	private String city;

	@Column(name = "COUNTRY", length = 16)
	private String country;
	
}
