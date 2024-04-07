package com.agro360.dto.finance;

import com.agro360.dto.common.AbstractDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "FIN_TBL_COMPTE")
public class CompteDto extends AbstractDto{

	@Id
	@Column(name = "COMPTE_CODE", updatable = false, length = 32)
	@EqualsAndHashCode.Include()
	private String compteCode;

	@Column(name = "DESCRIPTION", length = 64)
	private String description;
}
