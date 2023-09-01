package com.agro360.dto.tiers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import com.agro360.dto.common.AbstractStatusTrackingDto;
import com.agro360.vd.tiers.TiersStatusEnumVd;
import com.agro360.vd.tiers.TiersTypeEnumVd;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "TIERS_TBL_TIERS")
public class TiersDto extends AbstractStatusTrackingDto<TiersStatusEnumVd> {
	
	public static final int TIERS_CODE_LENGTH = 16;

	@Id
	@Column(name = "TIERS_CODE", updatable = false, length = TIERS_CODE_LENGTH)
	@EqualsAndHashCode.Include()
	private String tiersCode;

	@Enumerated(EnumType.STRING)
	@Column(name = "TIERS_TYPE", updatable = false, length = 8)
	private TiersTypeEnumVd tiersType;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, length = 16)
	private TiersStatusEnumVd status;

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
