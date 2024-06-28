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

	@Id
	@Column(name = "PARTNER_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private String partnerCode;

	@Enumerated(EnumType.STRING)
	@Column(name = "PARTNER_TYPE", updatable = false)
	private PartnerTypeEnumVd type;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private PartnerStatusEnumVd status;

	@Column(name = "NAME")
	private String name;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "CITY")
	private String city;

	@Column(name = "COUNTRY")
	private String country;

}
