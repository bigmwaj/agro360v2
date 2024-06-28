package com.agro360.dto.finance;

import com.agro360.dto.common.AbstractDto;
import com.agro360.dto.core.PartnerDto;
import com.agro360.vd.finance.CompteTypeEnumVd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "FIN_TBL_COMPTE")
public class CompteDto extends AbstractDto {

	@Id
	@Column(name = "COMPTE_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private String compteCode;

	@Enumerated(EnumType.STRING)
	@Column(name = "COMPTE_TYPE", updatable = false)
	private CompteTypeEnumVd type;

	@Column(name = "LIBELLE")
	private String libelle;

	@Column(name = "DESCRIPTION")
	private String description;

	@ManyToOne()
	@JoinColumn(name = "PARTNER_CODE", updatable = false)
	private PartnerDto partner;
}
